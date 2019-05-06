package com.bertan.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import com.bertan.data.local.db.LocalDatabase
import com.bertan.data.local.test.PostModelDataFactory
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PostDaoSpec {
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
        val model = PostModelDataFactory.get()
        database.postDao.insert(model)

        val result = database.postDao.posts().test()
        result.assertValue(listOf(model))
    }

    @Test
    fun `given an existing model when insert it should replace it`() {
        val model = PostModelDataFactory.get()
        database.postDao.insert(model)
        database.postDao.insert(model.copy(title = "otherName"))

        val result = database.postDao.posts().test()
        result.assertValue(listOf(model.copy(title = "otherName")))
    }

    @Test
    fun `given a response when call posts it should return all posts`() {
        val models = PostModelDataFactory.get(2)
        database.postDao.insert(models.first())
        database.postDao.insert(models.last())

        val result = database.postDao.posts().test()
        result.assertValue(models)
    }

    @Test
    fun `given another response when call posts it should return new posts`() {
        val models = PostModelDataFactory.get(2)
        database.postDao.insert(models.first())

        val result = database.postDao.posts().test()
        result.assertValue(listOf(models.first()))

        database.postDao.insert(models.last())

        val values = result.values().map { it.toSet() }
        Assert.assertThat(values, Matchers.hasSize(2))
        Assert.assertTrue(values.contains(setOf(models.first())))
        Assert.assertTrue(values.contains(models.toSet()))
    }

    @Test
    fun `given a found response when call post it should return the post`() {
        val models = PostModelDataFactory.get(2)
        database.postDao.insert(models.first())
        database.postDao.insert(models.last())

        val result = database.postDao.post(models.first().id).test()
        result.assertValue(models.first())
    }

    @Test
    fun `given a not found response when call post it should return an EmptyResultSetException`() {
        val models = PostModelDataFactory.get(2)
        database.postDao.insert(models.first())
        database.postDao.insert(models.last())

        val result = database.postDao.post("notFoundId").test()
        result.assertError(EmptyResultSetException::class.java)
    }
}