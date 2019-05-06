package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.account.GetAccounts
import com.bertan.domain.model.Account
import com.bertan.presentation.state.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AccountListViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var accountListViewModel: AccountListViewModel

    @MockK(relaxed = true)
    private lateinit var getAccountsUseCase: GetAccounts
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        accountListViewModel = AccountListViewModel(getAccountsUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(accountListViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should execute use case`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        verify(exactly = 1) { getAccountsUseCase.execute(null, any(), any(), any()) }
        confirmVerified(getAccountsUseCase)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should post loading state`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Loading, accountListViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes it should post success state`() {
        every { getAccountsUseCase.execute(null, captureLambda(), any(), any()) } answers {
            lambda<Function1<List<Account>, Unit>>().invoke(emptyList())
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Success(emptyList<Account>()), accountListViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getAccountsUseCase.execute(null, any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Error("Failed to load Accounts!", dummyError), accountListViewModel.getState().value)
    }
}