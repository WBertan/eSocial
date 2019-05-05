package com.bertan.domain.interactor.comment

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.CommentDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class GetCommentsByPostSpec {
    private lateinit var getCommentsByPost: GetCommentsByPost

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getCommentsByPost = GetCommentsByPost(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getCommentsByPost(any()) } returns Observable.just(emptyList())

        val result = getCommentsByPost.buildUseCase(GetCommentsByPost.Param("postId")).test()

        result.assertComplete()
    }

    @Test
    fun `given a null param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getCommentsByPost.buildUseCase(null)
    }

    @Test
    fun `given an empty param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getCommentsByPost.buildUseCase()
    }

    @Test
    fun `given a response when executes it should return data`() {
        val comments = CommentDataFactory.get(2)
        every { repository.getCommentsByPost(any()) } returns Observable.just(comments)

        val result = getCommentsByPost.buildUseCase(GetCommentsByPost.Param("postId")).test()

        result.assertValue(comments)
    }
}