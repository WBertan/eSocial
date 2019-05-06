package com.bertan.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import com.bertan.data.local.db.LocalDatabase
import com.bertan.data.local.test.CommentModelDataFactory
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CommentDaoSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database =
        Room
            .inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.applicationContext,
                LocalDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()

    @After
    fun tearDown() = database.close()

    @Test
    fun `given a model when insert it should save it`() {
        val model = CommentModelDataFactory.get()
        database.commentDao.insert(model)

        val result = database.commentDao.commentsByPost(model.postId).test()
        result.assertValue(listOf(model))
    }

    @Test
    fun `given an existing model when insert it should replace it`() {
        val model = CommentModelDataFactory.get()
        database.commentDao.insert(model)
        database.commentDao.insert(model.copy(userName = "otherName"))

        val result = database.commentDao.commentsByPost(model.postId).test()
        result.assertValue(listOf(model.copy(userName = "otherName")))
    }

    @Test
    fun `given a response when call commentsByPost it should return all comments for the post`() {
        val models = CommentModelDataFactory.get(2)
        database.commentDao.insert(models.first())
        database.commentDao.insert(models.last())

        val result = database.commentDao.commentsByPost(models.first().postId).test()
        result.assertValue(listOf(models.first()))
    }

    @Test
    fun `given another response when call commentsByPost it should return new comments for the post`() {
        val models = CommentModelDataFactory.get(2).map { it.copy(postId = "postId") }
        database.commentDao.insert(models.first())

        val result = database.commentDao.commentsByPost("postId").test()
        result.assertValue(listOf(models.first()))

        database.commentDao.insert(models.last())

        val values = result.values().map { it.toSet() }
        assertThat(values, hasSize(2))
        assertTrue(values.contains(setOf(models.first())))
        assertTrue(values.contains(models.toSet()))
    }

    @Test
    fun `given a found response when call comment it should return the comment`() {
        val models = CommentModelDataFactory.get(2)
        database.commentDao.insert(models.first())
        database.commentDao.insert(models.last())

        val result = database.commentDao.comment(models.first().postId, models.first().id).test()
        result.assertValue(models.first())
    }

    @Test
    fun `given a not found by postId response when call comment it should return an EmptyResultSetException`() {
        val models = CommentModelDataFactory.get(2).map { it.copy(postId = "postId") }
        database.commentDao.insert(models.first())
        database.commentDao.insert(models.last())

        val result = database.commentDao.comment("notFoundId", models.first().id).test()
        result.assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun `given a not found by commentId response when call comment it should return an EmptyResultSetException`() {
        val models = CommentModelDataFactory.get(2).map { it.copy(postId = "postId") }
        database.commentDao.insert(models.first())
        database.commentDao.insert(models.last())

        val result = database.commentDao.comment(models.first().postId, "notFoundId").test()
        result.assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun `given a not found response when call comment it should return an EmptyResultSetException`() {
        val models = CommentModelDataFactory.get(2)
        database.commentDao.insert(models.first())
        database.commentDao.insert(models.last())

        val result = database.commentDao.comment("notFoundId", "notFoundId").test()
        result.assertError(EmptyResultSetException::class.java)
    }
}