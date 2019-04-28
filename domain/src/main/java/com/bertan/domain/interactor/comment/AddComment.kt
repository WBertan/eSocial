package com.bertan.domain.interactor.comment

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.CompletableUseCase
import com.bertan.domain.model.Comment
import com.bertan.domain.repository.Repository
import io.reactivex.Completable

class AddComment(
    private val repository: Repository,
    executor: SchedulerExecutor
) : CompletableUseCase<AddComment.Param>(executor) {

    override fun buildUseCase(params: Param?): Completable =
        params.validate { repository.addComment(it.comment) }

    data class Param(val comment: Comment)
}