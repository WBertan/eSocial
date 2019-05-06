package com.bertan.presentation.vm

import com.bertan.domain.interactor.account.GetAccounts
import com.bertan.presentation.mapper.AccountMapper.asAccountView
import com.bertan.presentation.model.AccountView

class AccountListViewModel(private val getAccountsUseCase: GetAccounts) :
    RxUseCaseViewModel<List<AccountView>>(getAccountsUseCase) {
    override fun onCreateViewModel() {
        postLoading()

        getAccountsUseCase.execute(
            onNext = { accounts -> accounts.map { it.asAccountView }.postSuccess() },
            onError = { it.postError("Failed to load Accounts!") }
        )
    }
}