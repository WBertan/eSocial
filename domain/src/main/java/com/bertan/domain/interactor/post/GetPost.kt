package com.bertan.domain.interactor.post

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Post
import com.bertan.domain.repository.Repository
import io.reactivex.Observable
import java.util.*

class GetPost(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<Optional<Post>, GetPost.Param>(executor) {

    override fun buildUseCase(params: Param?): Observable<Optional<Post>> =
        params.validate { repository.getPost(it.accountId, it.postId) }

    data class Param(val accountId: String, val postId: String)
}