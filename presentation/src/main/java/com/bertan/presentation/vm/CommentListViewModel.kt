package com.bertan.presentation.vm

import com.bertan.domain.interactor.comment.GetCommentsByPost
import com.bertan.presentation.mapper.CommentMapper.asCommentView
import com.bertan.presentation.model.CommentView

class CommentListViewModel(private val postId: String, private val getCommentsByPostUseCase: GetCommentsByPost) :
    RxUseCaseViewModel<List<CommentView>>(getCommentsByPostUseCase) {
    override fun onCreateViewModel() {
        postLoading()

        getCommentsByPostUseCase.execute(
            GetCommentsByPost.Param(postId),
            onNext = { comments -> comments.map { it.asCommentView }.postSuccess() },
            onError = { it.postError("Failed to load Comments for Post($postId)!") }
        )
    }
}