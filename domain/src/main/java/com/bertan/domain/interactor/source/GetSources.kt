package com.bertan.domain.interactor.source

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Source
import com.bertan.domain.repository.Repository
import io.reactivex.Observable

class GetSources(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<List<Source>, Nothing>(executor) {

    override fun buildUseCase(params: Nothing?): Observable<List<Source>> =
        repository.getSources()
            .map { sources -> sources.sortedBy { it.name } }
}