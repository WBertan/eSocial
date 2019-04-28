package com.bertan.domain.interactor.account

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Account
import com.bertan.domain.repository.Repository
import io.reactivex.Observable

class GetAccounts(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<List<Account>, Nothing>(executor) {

    override fun buildUseCase(params: Nothing?): Observable<List<Account>> =
        repository.getAccounts()
}