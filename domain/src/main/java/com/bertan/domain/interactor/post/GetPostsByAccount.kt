package com.bertan.domain.interactor.post

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Post
import com.bertan.domain.repository.Repository
import io.reactivex.Observable

class GetPostsByAccount(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<List<Post>, GetPostsByAccount.Param>(executor) {

    override fun buildUseCase(params: Param?): Observable<List<Post>> =
        params.validate { repository.getPostsByAccount(it.accountId) }

    data class Param(val accountId: String)
}