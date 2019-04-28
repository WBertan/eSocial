package com.bertan.domain.interactor.account

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Account
import com.bertan.domain.repository.Repository
import io.reactivex.Observable
import java.util.*

class GetAccount(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<Optional<Account>, GetAccount.Param>(executor) {

    override fun buildUseCase(params: Param?): Observable<Optional<Account>> =
        params.validate { repository.getAccount(it.accountId) }

    data class Param(val accountId: String)
}