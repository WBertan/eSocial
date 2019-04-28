package com.bertan.domain.interactor.post

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.PostDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*

class GetPostSpec {
    private lateinit var getPost: GetPost

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getPost = GetPost(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getPost(any(), any()) } returns Observable.just(Optional.of(PostDataFactory.get()))

        val result = getPost.buildUseCase(GetPost.Param("accountId", "postId")).test()

        result.assertComplete()
    }

    @Test
    fun `given a null param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getPost.buildUseCase(null)
    }

    @Test
    fun `given an empty param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getPost.buildUseCase()
    }

    @Test
    fun `given a found response when executes it should return some data`() {
        val post = PostDataFactory.get()
        every { repository.getPost(any(), any()) } returns Observable.just(Optional.of(post))

        val result = getPost.buildUseCase(GetPost.Param(post.accountId, post.id)).test()

        result.assertValue(Optional.of(post))
    }

    @Test
    fun `given a not found response when executes it should return none data`() {
        every { repository.getPost(any(), any()) } returns Observable.just(Optional.empty())

        val result = getPost.buildUseCase(GetPost.Param("notFoundId", "notFoundId")).test()

        result.assertValue(Optional.empty())
    }
}