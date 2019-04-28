package com.bertan.domain.interactor.account

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.AccountDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*

class GetAccountSpec {
    private lateinit var getAccount: GetAccount

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @get:Rule
    val exceptionRule = ExpectedException.none()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAccount = GetAccount(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getAccount(any()) } returns Observable.just(Optional.of(AccountDataFactory.get()))

        val result = getAccount.buildUseCase(GetAccount.Param("accountId")).test()

        result.assertComplete()
    }

    @Test
    fun `given a null param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getAccount.buildUseCase(null)
    }

    @Test
    fun `given an empty param when executes it should throw IllegalArgumentException`() {
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage("Parameter should not be null!")
        getAccount.buildUseCase()
    }

    @Test
    fun `given a found response when executes it should return some data`() {
        val account = AccountDataFactory.get()
        every { repository.getAccount(any()) } returns Observable.just(Optional.of(account))

        val result = getAccount.buildUseCase(GetAccount.Param(account.id)).test()

        result.assertValue(Optional.of(account))
    }

    @Test
    fun `given a not found response when executes it should return none data`() {
        every { repository.getAccount(any()) } returns Observable.just(Optional.empty())

        val result = getAccount.buildUseCase(GetAccount.Param("notFoundId")).test()

        result.assertValue(Optional.empty())
    }
}