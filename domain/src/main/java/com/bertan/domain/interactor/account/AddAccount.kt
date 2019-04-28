package com.bertan.domain.interactor.account

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.CompletableUseCase
import com.bertan.domain.model.Account
import com.bertan.domain.repository.Repository
import io.reactivex.Completable

class AddAccount(
    private val repository: Repository,
    executor: SchedulerExecutor
) : CompletableUseCase<AddAccount.Param>(executor) {

    override fun buildUseCase(params: Param?): Completable =
        params.validate { repository.addAccount(it.account) }

    data class Param(val account: Account)
}