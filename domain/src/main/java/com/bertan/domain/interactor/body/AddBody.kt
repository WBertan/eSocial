package com.bertan.domain.interactor.body

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.CompletableUseCase
import com.bertan.domain.model.Body
import com.bertan.domain.repository.Repository
import io.reactivex.Completable

class AddBody(
    private val repository: Repository,
    executor: SchedulerExecutor
) : CompletableUseCase<AddBody.Param>(executor) {

    override fun buildUseCase(params: Param?): Completable =
        params.validate { repository.addBody(it.body) }

    data class Param(val body: Body)
}