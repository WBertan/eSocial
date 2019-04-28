package com.bertan.domain.interactor.post

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.domain.interactor.ObservableUseCase
import com.bertan.domain.model.Post
import com.bertan.domain.repository.Repository
import io.reactivex.Observable

class GetPosts(
    private val repository: Repository,
    executor: SchedulerExecutor
) : ObservableUseCase<List<Post>, Nothing>(executor) {

    override fun buildUseCase(params: Nothing?): Observable<List<Post>> =
        repository.getPosts()
}