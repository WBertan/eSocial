package com.bertan.presentation.vm

import com.bertan.domain.interactor.account.AddAccount
import com.bertan.domain.interactor.account.GetAccount
import com.bertan.presentation.mapper.AccountMapper.asAccountView
import com.bertan.presentation.mapper.AccountViewMapper.asAccount
import com.bertan.presentation.model.AccountView
import java.util.*

class AccountViewModel(private val getAccountUseCase: GetAccount, private val addAccountUseCase: AddAccount) :
    RxUseCaseViewModel<Optional<AccountView>>(getAccountUseCase, addAccountUseCase) {
    override fun onCreateViewModel() = Unit

    fun getAccount(accountId: String) {
        postLoading()

        getAccountUseCase.execute(
            GetAccount.Param(accountId),
            onNext = { account -> account.map { it.asAccountView }.postSuccess() },
            onError = { it.postError("Failed to load Account($accountId)!") }
        )
    }

    fun addAccount(account: AccountView) {
        postLoading()

        addAccountUseCase.execute(
            AddAccount.Param(account.asAccount),
            onComplete = { Optional.of(account).postSuccess() },
            onError = { it.postError("Failed to add $account!") }
        )
    }
}