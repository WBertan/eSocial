package com.bertan.data.remote.repository

import com.bertan.data.remote.mapper.CommentMapper.asCommentEntity
import com.bertan.data.remote.mapper.PostMapper.asPostEntity
import com.bertan.data.remote.service.JSONPlaceholderService
import com.bertan.data.remote.test.CommentModelDataFactory
import com.bertan.data.remote.test.PostModelDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

class RemoteDataSourceImplSpec {
    private lateinit var dataSource: RemoteDataSourceImpl

    @MockK
    lateinit var service: JSONPlaceholderService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = RemoteDataSourceImpl(service)
    }

    private fun <T> TestObserver<T>.assertCompletedValue(expected: T) {
        assertComplete()
        assertValue(expected)
    }

    @Test
    fun `given a response when getPosts it should completes and return data`() {
        val posts = PostModelDataFactory.get(2)
        val postsEntity = posts.map { it.asPostEntity }
        every { service.getPosts() } returns Observable.just(posts)

        val result = dataSource.getPosts().test()

        result.assertCompletedValue(postsEntity)
    }

    @Test
    fun `given a response when getPost it should completes and return data`() {
        val post = PostModelDataFactory.get()
        val postEntity = post.asPostEntity
        every { service.getPost(any()) } returns Observable.just(post)

        val result = dataSource.getPost("0").test()

        result.assertCompletedValue(Optional.of(postEntity))
    }

    @Test
    fun `given invalid postId when getPost it should return error`() {
        val result = dataSource.getPost("invalidPostId").test()

        result.assertErrorMessage("Invalid postId.")
    }

    @Test
    fun `given a response when getCommentsByPost it should completes and return data`() {
        val comments = CommentModelDataFactory.get(2)
        val commentsEntity = comments.map { it.asCommentEntity }
        every { service.getCommentsByPost(any()) } returns Observable.just(comments)

        val result = dataSource.getCommentsByPost("0").test()

        result.assertCompletedValue(commentsEntity)
    }

    @Test
    fun `given invalid postId when getCommentsByPost it should return error`() {
        val result = dataSource.getCommentsByPost("invalidPostId").test()

        result.assertErrorMessage("Invalid postId.")
    }

    @Test
    fun `given a response when getComment it should completes and return data`() {
        val comment = CommentModelDataFactory.get()
        val commentEntity = comment.asCommentEntity
        every { service.getComment(any()) } returns Observable.just(comment)

        val result = dataSource.getComment("0", "0").test()

        result.assertCompletedValue(Optional.of(commentEntity))
    }

    @Test
    fun `given invalid commentId when getComment it should return error`() {
        val result = dataSource.getComment("0", "invalidCommentId").test()

        result.assertErrorMessage("Invalid commentId.")
    }

    @Test
    fun `given invalid postId when getComment it should return error`() {
        val result = dataSource.getComment("invalidPostId", "0").test()

        result.assertErrorMessage("Invalid postId.")
    }

    @Test
    fun `given invalid postId and commentId when getComment it should return error`() {
        val result = dataSource.getComment("invalidPostId", "invalidCommentId").test()

        result.assertErrorMessage("Invalid postId.")
    }
}