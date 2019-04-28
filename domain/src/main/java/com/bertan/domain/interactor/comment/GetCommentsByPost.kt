package com.bertan.domain.interactor.comment

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Comment
import com.bertan.domain.repository.Repository
import io.reactivex.Observable

class GetCommentsByPost(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<List<Comment>, GetCommentsByPost.Param>(executor) {

    override fun buildUseCase(params: Param?): Observable<List<Comment>> =
        params.validate { repository.getCommentsByPost(it.accountId, it.postId) }

    data class Param(val accountId: String, val postId: String)
}