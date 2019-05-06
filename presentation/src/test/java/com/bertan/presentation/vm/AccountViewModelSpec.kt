package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.account.AddAccount
import com.bertan.domain.interactor.account.GetAccount
import com.bertan.domain.model.Account
import com.bertan.presentation.mapper.AccountMapper.asAccountView
import com.bertan.presentation.state.ViewState
import com.bertan.presentation.test.AccountDataFactory
import com.bertan.presentation.test.AccountViewDataFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class AccountViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var accountViewModel: AccountViewModel

    @MockK(relaxed = true)
    private lateinit var getAccountUseCase: GetAccount
    @MockK(relaxed = true)
    private lateinit var addAccountUseCase: AddAccount
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        accountViewModel = AccountViewModel(getAccountUseCase, addAccountUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(accountViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should not interact with the use cases`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        confirmVerified(getAccountUseCase)
        confirmVerified(addAccountUseCase)
    }

    @Test
    fun `given an accountId when call getAccount it should post loading state`() {
        accountViewModel.getAccount("accountId")

        assertEquals(ViewState.Loading, accountViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes getAccount it should post success state`() {
        val account = AccountDataFactory.get()
        every { getAccountUseCase.execute(any(), captureLambda(), any(), any()) } answers {
            lambda<Function1<Optional<Account>, Unit>>().invoke(Optional.of(account))
            mockk()
        }

        accountViewModel.getAccount("accountId")

        assertEquals(ViewState.Success(Optional.of(account.asAccountView)), accountViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes getAccount it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getAccountUseCase.execute(any(), any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        accountViewModel.getAccount("accountId")

        assertEquals(
            ViewState.Error("Failed to load Account(accountId)!", dummyError),
            accountViewModel.getState().value
        )
    }

    @Test
    fun `given an account when call addAccount it should post loading state`() {
        val accountView = AccountViewDataFactory.get()
        accountViewModel.addAccount(accountView)

        assertEquals(ViewState.Loading, accountViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes addAccount it should post success state`() {
        val accountView = AccountViewDataFactory.get()
        every { addAccountUseCase.execute(any(), captureLambda(), any()) } answers {
            lambda<Function0<Unit>>().invoke()
            mockk()
        }

        accountViewModel.addAccount(accountView)

        assertEquals(ViewState.Success(Optional.of(accountView)), accountViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes addAccount it should post error state`() {
        val accountView = AccountViewDataFactory.get()
        val dummyError = Exception("dummyError")
        every { addAccountUseCase.execute(any(), any(), captureLambda()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        accountViewModel.addAccount(accountView)

        assertEquals(
            ViewState.Error("Failed to add ${accountView}!", dummyError),
            accountViewModel.getState().value
        )
    }
}