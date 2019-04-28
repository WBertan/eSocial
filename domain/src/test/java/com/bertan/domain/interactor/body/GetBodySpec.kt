package com.bertan.domain.interactor.body

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.BodyDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*

class GetBodySpec {
    private lateinit var getBody: GetBody

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getBody = GetBody(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getBody(any(), any()) } returns Observable.just(Optional.of(BodyDataFactory.get()))

        val result = getBody.buildUseCase(GetBody.Param("accountId", "bodyId")).test()

        result.assertComplete()
    }

    @Test
    fun `given a null param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getBody.buildUseCase(null)
    }

    @Test
    fun `given an empty param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getBody.buildUseCase()
    }

    @Test
    fun `given a found response when executes it should return some data`() {
        val body = BodyDataFactory.get()
        every { repository.getBody(any(), any()) } returns Observable.just(Optional.of(body))

        val result = getBody.buildUseCase(GetBody.Param(body.accountId, body.id)).test()

        result.assertValue(Optional.of(body))
    }

    @Test
    fun `given a not found response when executes it should return none data`() {
        every { repository.getBody(any(), any()) } returns Observable.just(Optional.empty())

        val result = getBody.buildUseCase(GetBody.Param("notFoundId", "notFoundId")).test()

        result.assertValue(Optional.empty())
    }
}