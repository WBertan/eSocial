package com.bertan.presentation.vm

import com.bertan.domain.interactor.post.AddPost
import com.bertan.domain.interactor.post.GetPost
import com.bertan.presentation.mapper.PostMapper.asPostView
import com.bertan.presentation.mapper.PostViewMapper.asPost
import com.bertan.presentation.model.PostView
import java.util.*

class PostViewModel(private val getPostUseCase: GetPost, private val addPostUseCase: AddPost) :
    RxUseCaseViewModel<Optional<PostView>>(getPostUseCase, addPostUseCase) {
    override fun onCreateViewModel() = Unit

    fun getPost(postId: String) {
        postLoading()

        getPostUseCase.execute(
            GetPost.Param(postId),
            onNext = { post -> post.map { it.asPostView }.postSuccess() },
            onError = { it.postError("Failed to load Post($postId)!") }
        )
    }

    fun addPost(post: PostView) {
        postLoading()

        addPostUseCase.execute(
            AddPost.Param(post.asPost),
            onComplete = { Optional.of(post).postSuccess() },
            onError = { it.postError("Failed to add $post!") }
        )
    }
}