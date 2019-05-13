package com.bertan.data.local.repository

import androidx.room.EmptyResultSetException
import com.bertan.data.local.db.LocalDatabase
import com.bertan.data.local.mapper.AccountModelMapper.asAccountEntity
import com.bertan.data.local.mapper.CommentModelMapper.asCommentEntity
import com.bertan.data.local.mapper.PostModelMapper.asPostEntity
import com.bertan.data.local.test.AccountModelDataFactory
import com.bertan.data.local.test.CommentModelDataFactory
import com.bertan.data.local.test.PostModelDataFactory
import com.bertan.data.model.SourceEntity
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class LocalDataSourceImplSpec {
    private lateinit var dataSource: LocalDataSourceImpl

    @MockK
    lateinit var database: LocalDatabase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        mockkConstructor(LocalDatabase.Factory::class)
        every { anyConstructed<LocalDatabase.Factory>()() } returns database

        dataSource = LocalDataSourceImpl(mockk())
    }

    @After
    fun tearDown() {
        unmockkConstructor(LocalDatabase.Factory::class)
    }

    private fun <T> TestObserver<T>.assertCompletedValue(expected: T) {
        assertComplete()
        assertValue(expected)
    }

    @Test
    fun `given a response when getSources it should completes and return data`() {
        val result = dataSource.getSources().test()

        result.assertComplete()
        result.assertValue { sources ->
            val (enabledSources, disabledSources) =
                sources.partition { it.state == SourceEntity.StateEntity.Enabled }
            val onlyMainSourceEnabled =
                enabledSources.single().let {
                    it.id == "1" && it.name == "JSONPlaceholder"
                }
            val otherSourcesDisabled = disabledSources.size == 2
            onlyMainSourceEnabled && otherSourcesDisabled
        }

        confirmVerified(database)
    }

    @Test
    fun `given a response when getAccounts it should completes and return data`() {
        val accountsModel = AccountModelDataFactory.get(2)
        val accountsEntity = accountsModel.map { it.asAccountEntity }

        every { database.accountDao.accounts() } returns Observable.just(accountsModel)

        val result = dataSource.getAccounts().test()

        result.assertCompletedValue(accountsEntity)
    }

    @Test
    fun `given a found response when getAccount it should completes and return data`() {
        val accountModel = AccountModelDataFactory.get()
        val accountEntity = accountModel.asAccountEntity

        every { database.accountDao.account(any()) } returns Single.just(accountModel)

        val result = dataSource.getAccount("accountId").test()

        result.assertCompletedValue(Optional.of(accountEntity))
    }

    @Test
    fun `given a not found response when getAccount it should completes and return data`() {
        every { database.accountDao.account(any()) } returns Single.error(EmptyResultSetException("dummyMessage"))

        val result = dataSource.getAccount("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addAccount it should completes`() {
        val accountModel = AccountModelDataFactory.get()
        val accountEntity = accountModel.asAccountEntity

        every { database.accountDao.insert(any()) } returns Unit

        val result = dataSource.addAccount(accountEntity).test()

        result.assertComplete()
        verify { database.accountDao.insert(accountModel) }
    }

    @Test
    fun `given a response when getPosts it should completes and return data`() {
        val postsModel = PostModelDataFactory.get(2)
        val postsEntity = postsModel.map { it.asPostEntity }

        every { database.postDao.posts() } returns Flowable.just(postsModel)

        val result = dataSource.getPosts().test()

        result.assertCompletedValue(postsEntity)
    }

    @Test
    fun `given a found response when getPost it should completes and return data`() {
        val postModel = PostModelDataFactory.get()
        val postEntity = postModel.asPostEntity

        every { database.postDao.post(any()) } returns Single.just(postModel)

        val result = dataSource.getPost("postId").test()

        result.assertCompletedValue(Optional.of(postEntity))
    }

    @Test
    fun `given a not found response when getPost it should completes and return data`() {
        every { database.postDao.post(any()) } returns Single.error(EmptyResultSetException("dummyMessage"))

        val result = dataSource.getPost("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addPost it should completes`() {
        val postModel = PostModelDataFactory.get()
        val postEntity = postModel.asPostEntity

        every { database.postDao.insert(any()) } returns Unit

        val result = dataSource.addPost(postEntity).test()

        result.assertComplete()
        verify { database.postDao.insert(postModel) }
    }

    @Test
    fun `given a response when getCommentsByPost it should completes and return data`() {
        val commentsModel = CommentModelDataFactory.get(2)
        val commentsEntity = commentsModel.map { it.asCommentEntity }

        every { database.commentDao.commentsByPost(any()) } returns Flowable.just(commentsModel)

        val result = dataSource.getCommentsByPost("postId").test()

        result.assertCompletedValue(commentsEntity)
    }

    @Test
    fun `given a found response when getComment it should completes and return data`() {
        val commentModel = CommentModelDataFactory.get()
        val commentEntity = commentModel.asCommentEntity

        every { database.commentDao.comment(any(), any()) } returns Single.just(commentModel)

        val result = dataSource.getComment("postId", "commentId").test()

        result.assertCompletedValue(Optional.of(commentEntity))
    }

    @Test
    fun `given a not found response when getComment it should completes and return data`() {
        every {
            database.commentDao.comment(
                any(),
                any()
            )
        } returns Single.error(EmptyResultSetException("dummyMessage"))

        val result = dataSource.getComment("notFoundId", "notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addComment it should completes`() {
        val commentModel = CommentModelDataFactory.get()
        val commentEntity = commentModel.asCommentEntity

        every { database.commentDao.insert(any()) } returns Unit

        val result = dataSource.addComment(commentEntity).test()

        result.assertComplete()
        verify { database.commentDao.insert(commentModel) }
    }
}