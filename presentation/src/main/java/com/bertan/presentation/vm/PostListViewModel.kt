package com.bertan.presentation.vm

import com.bertan.domain.interactor.post.GetPosts
import com.bertan.presentation.mapper.PostMapper.asPostView
import com.bertan.presentation.model.PostView

class PostListViewModel(private val getPostsUseCase: GetPosts) :
    RxUseCaseViewModel<List<PostView>>(getPostsUseCase) {
    override fun onCreateViewModel() {
        postLoading()

        getPostsUseCase.execute(
            onNext = { posts -> posts.map { it.asPostView }.postSuccess() },
            onError = { it.postError("Failed to load Posts!") }
        )
    }
}