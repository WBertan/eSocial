package com.bertan.data.store

import com.bertan.data.repository.LocalDataSource
import com.bertan.data.test.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

class LocalDataStoreSpec {
    lateinit var localDataStore: LocalDataStore

    @MockK
    lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        localDataStore = LocalDataStore(localDataSource)
    }

    private fun <T> TestObserver<T>.assertCompletedValue(expected: T) {
        assertComplete()
        assertValue(expected)
    }

    @Test
    fun `given a response when getSources it should completes and return data`() {
        val sources = SourceEntityDataFactory.get(2)
        every { localDataSource.getSources() } returns Observable.just(sources)

        val result = localDataStore.getSources().test()

        result.assertCompletedValue(sources)
    }

    @Test
    fun `given a response when getAccounts it should completes and return data`() {
        val accounts = AccountEntityDataFactory.get(2)
        every { localDataSource.getAccounts() } returns Observable.just(accounts)

        val result = localDataStore.getAccounts().test()

        result.assertCompletedValue(accounts)
    }

    @Test
    fun `given a found response when getAccount it should completes and return data`() {
        val account = AccountEntityDataFactory.get()
        every { localDataSource.getAccount(any()) } returns Observable.just(Optional.of(account))

        val result = localDataStore.getAccount("accountId").test()

        result.assertCompletedValue(Optional.of(account))
    }

    @Test
    fun `given a not found response when getAccount it should completes and return data`() {
        every { localDataSource.getAccount(any()) } returns Observable.just(Optional.empty())

        val result = localDataStore.getAccount("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addAccount it should completes`() {
        every { localDataSource.addAccount(any()) } returns Completable.complete()

        val result = localDataStore.addAccount(AccountEntityDataFactory.get()).test()

        result.assertComplete()
    }

    @Test
    fun `given a response when getPosts it should completes and return data`() {
        val posts = PostEntityDataFactory.get(2)
        every { localDataSource.getPosts() } returns Observable.just(posts)

        val result = localDataStore.getPosts().test()

        result.assertCompletedValue(posts)
    }

    @Test
    fun `given a found response when getPost it should completes and return data`() {
        val post = PostEntityDataFactory.get()
        every { localDataSource.getPost(any()) } returns Observable.just(Optional.of(post))

        val result = localDataStore.getPost("postId").test()

        result.assertCompletedValue(Optional.of(post))
    }

    @Test
    fun `given a not found response when getPost it should completes and return data`() {
        every { localDataSource.getPost(any()) } returns Observable.just(Optional.empty())

        val result = localDataStore.getPost("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addPost it should completes`() {
        every { localDataSource.addPost(any()) } returns Completable.complete()

        val result = localDataStore.addPost(PostEntityDataFactory.get()).test()

        result.assertComplete()
    }

    @Test
    fun `given a response when getCommentsByPost it should completes and return data`() {
        val comments = CommentEntityDataFactory.get(2)
        every { localDataSource.getCommentsByPost(any()) } returns Observable.just(comments)

        val result = localDataStore.getCommentsByPost("postId").test()

        result.assertCompletedValue(comments)
    }

    @Test
    fun `given a found response when getComment it should completes and return data`() {
        val comment = CommentEntityDataFactory.get()
        every { localDataSource.getComment(any(), any()) } returns Observable.just(Optional.of(comment))

        val result = localDataStore.getComment("postId", "commentId").test()

        result.assertCompletedValue(Optional.of(comment))
    }

    @Test
    fun `given a not found response when getComment it should completes and return data`() {
        every { localDataSource.getComment(any(), any()) } returns Observable.just(Optional.empty())

        val result = localDataStore.getComment("notFoundId", "notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addComment it should completes`() {
        every { localDataSource.addComment(any()) } returns Completable.complete()

        val result = localDataStore.addComment(CommentEntityDataFactory.get()).test()

        result.assertComplete()
    }

    @Test
    fun `given a found response when getBody it should completes and return data`() {
        val body = BodyEntityDataFactory.get()
        every { localDataSource.getBody(any()) } returns Observable.just(Optional.of(body))

        val result = localDataStore.getBody("bodyId").test()

        result.assertCompletedValue(Optional.of(body))
    }

    @Test
    fun `given a not found response when getBody it should completes and return data`() {
        every { localDataSource.getBody(any()) } returns Observable.just(Optional.empty())

        val result = localDataStore.getBody("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addBody it should completes`() {
        every { localDataSource.addBody(any()) } returns Completable.complete()

        val result = localDataStore.addBody(BodyEntityDataFactory.get()).test()

        result.assertComplete()
    }
}