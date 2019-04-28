package com.bertan.domain.interactor.account

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.repository.Repository
import com.bertan.domain.test.AccountDataFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetAccountsSpec {
    private lateinit var getAccounts: GetAccounts

    @MockK
    private lateinit var repository: Repository
    @MockK
    private lateinit var executor: SchedulerExecutor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAccounts = GetAccounts(repository, executor)
    }

    @Test
    fun `given a response when executes it should completes`() {
        every { repository.getSources() } returns Observable.just(emptyList())

        val result = getAccounts.buildUseCase().test()

        result.assertComplete()
    }

    @Test
    fun `given a response when executes it should return data`() {
        val accounts = AccountDataFactory.get(2)
        every { repository.getAccounts() } returns Observable.just(accounts)

        val result = getAccounts.buildUseCase().test()

        result.assertValue(accounts)
    }
}