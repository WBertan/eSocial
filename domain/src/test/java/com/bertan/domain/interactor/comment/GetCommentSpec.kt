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
import java.util.*

class GetCommentSpec {
    private lateinit var getComment: GetComment

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getComment = GetComment(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getComment(any(), any(), any()) } returns Observable.just(Optional.of(CommentDataFactory.get()))

        val result = getComment.buildUseCase(GetComment.Param("accountId", "postId", "commentId")).test()

        result.assertComplete()
    }

    @Test
    fun `given a null param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getComment.buildUseCase(null)
    }

    @Test
    fun `given an empty param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getComment.buildUseCase()
    }

    @Test
    fun `given a found response when executes it should return some data`() {
        val comment = CommentDataFactory.get()
        every { repository.getComment(any(), any(), any()) } returns Observable.just(Optional.of(comment))

        val result = getComment.buildUseCase(GetComment.Param(comment.accountId, comment.postId, comment.id)).test()

        result.assertValue(Optional.of(comment))
    }

    @Test
    fun `given a not found response when executes it should return none data`() {
        every { repository.getComment(any(), any(), any()) } returns Observable.just(Optional.empty())

        val result = getComment.buildUseCase(GetComment.Param("notFoundId", "notFoundId", "notFoundId")).test()

        result.assertValue(Optional.empty())
    }
}