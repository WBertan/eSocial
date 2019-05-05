package com.bertan.domain.interactor.comment

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Comment
import com.bertan.domain.repository.Repository
import io.reactivex.Observable
import java.util.*

class GetComment(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<Optional<Comment>, GetComment.Param>(executor) {

    override fun buildUseCase(params: Param?): Observable<Optional<Comment>> =
        params.validate { repository.getComment(it.postId, it.commentId) }

    data class Param(val postId: String, val commentId: String)
}