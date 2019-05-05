package com.bertan.data.store

import com.bertan.data.repository.RemoteDataSource
import com.bertan.data.test.AccountEntityDataFactory
import com.bertan.data.test.CommentEntityDataFactory
import com.bertan.data.test.PostEntityDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

class RemoteDataStoreSpec {
    lateinit var remoteDataStore: RemoteDataStore

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        remoteDataStore = RemoteDataStore(remoteDataSource)
    }

    private fun <T> TestObserver<T>.assertCompletedValue(expected: T) {
        assertComplete()
        assertValue(expected)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `when getSources it should throw exception`() {
        remoteDataStore.getSources()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `when getAccounts it should throw exception`() {
        remoteDataStore.getAccounts()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `when getAccount it should throw exception`() {
        remoteDataStore.getAccount("accountId")
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `when addAccount it should throw exception`() {
        remoteDataStore.addAccount(AccountEntityDataFactory.get())
    }

    @Test
    fun `given a response when getPosts it should completes and return data`() {
        val posts = PostEntityDataFactory.get(2)
        every { remoteDataSource.getPosts() } returns Observable.just(posts)

        val result = remoteDataStore.getPosts().test()

        result.assertCompletedValue(posts)
    }

    @Test
    fun `given a found response when getPost it should completes and return data`() {
        val post = PostEntityDataFactory.get()
        every { remoteDataSource.getPost(any()) } returns Observable.just(Optional.of(post))

        val result = remoteDataStore.getPost("postId").test()

        result.assertCompletedValue(Optional.of(post))
    }

    @Test
    fun `given a not found response when getPost it should completes and return data`() {
        every { remoteDataSource.getPost(any()) } returns Observable.just(Optional.empty())

        val result = remoteDataStore.getPost("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `when addPost it should throw exception`() {
        remoteDataStore.addPost(PostEntityDataFactory.get())
    }

    @Test
    fun `given a response when getCommentsByPost it should completes and return data`() {
        val comments = CommentEntityDataFactory.get(2)
        every { remoteDataSource.getCommentsByPost(any()) } returns Observable.just(comments)

        val result = remoteDataStore.getCommentsByPost("postId").test()

        result.assertCompletedValue(comments)
    }

    @Test
    fun `given a found response when getComment it should completes and return data`() {
        val comment = CommentEntityDataFactory.get()
        every { remoteDataSource.getComment(any(), any()) } returns Observable.just(Optional.of(comment))

        val result = remoteDataStore.getComment("postId", "commentId").test()

        result.assertCompletedValue(Optional.of(comment))
    }

    @Test
    fun `given a not found response when getComment it should completes and return data`() {
        every { remoteDataSource.getComment(any(), any()) } returns Observable.just(Optional.empty())

        val result = remoteDataStore.getComment("notFoundId", "notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `when addComment it should throw exception`() {
        remoteDataStore.addComment(CommentEntityDataFactory.get())
    }
}