package com.bertan.domain.interactor.account

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.model.Account
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
        every { repository.getAccount(any()) } returns Observable.just(AccountDataFactory.get())

        val result = getAccount.buildUseCase().test()

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
    fun `given a found response when executes it should return data`() {
        val account = AccountDataFactory.get()
        every { repository.getAccount(any()) } returns Observable.just(account)

        val result = getAccount.buildUseCase(GetAccount.Param(account.id)).test()

        result.assertValue(account)
    }

    @Test
    fun `given a not found response when executes it should return data`() {
        every { repository.getAccount(any()) } returns Observable.just(null)

        val result = getAccount.buildUseCase(GetAccount.Param("notFoundId")).test()

        val expectedResult: Account? = null
        result.assertValue(expectedResult)
    }
}