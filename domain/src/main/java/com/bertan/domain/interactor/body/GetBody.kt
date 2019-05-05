package com.bertan.domain.interactor.body

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Body
import com.bertan.domain.repository.Repository
import io.reactivex.Observable
import java.util.*

class GetBody(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<Optional<Body>, GetBody.Param>(executor) {

    override fun buildUseCase(params: Param?): Observable<Optional<Body>> =
        params.validate { repository.getBody(it.bodyId) }

    data class Param(val bodyId: String)
}