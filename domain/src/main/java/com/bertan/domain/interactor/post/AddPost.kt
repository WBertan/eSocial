package com.bertan.domain.interactor.post

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.CompletableUseCase
import com.bertan.domain.model.Post
import com.bertan.domain.repository.Repository
import io.reactivex.Completable

class AddPost(
    private val repository: Repository,
    executor: SchedulerExecutor
) : CompletableUseCase<AddPost.Param>(executor) {

    override fun buildUseCase(params: Param?): Completable =
        params.validate { repository.addPost(it.post) }

    data class Param(val post: Post)
}