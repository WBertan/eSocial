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

class GetPostsByAccountSpec {
    private lateinit var getPostsByAccount: GetPostsByAccount

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @get:Rule
    val exceptionRule = ExpectedException.none()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getPostsByAccount = GetPostsByAccount(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getPostsByAccount(any()) } returns Observable.just(emptyList())

        val result = getPostsByAccount.buildUseCase(GetPostsByAccount.Param("accountId")).test()

        result.assertComplete()
    }

    @Test
    fun `given a null param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getPostsByAccount.buildUseCase(null)
    }

    @Test
    fun `given an empty param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getPostsByAccount.buildUseCase()
    }

    @Test
    fun `given a response when executes it should return data`() {
        val posts = PostDataFactory.get(2)
        every { repository.getPostsByAccount(any()) } returns Observable.just(posts)

        val result = getPostsByAccount.buildUseCase(GetPostsByAccount.Param("accountId")).test()

        result.assertValue(posts)
    }
}