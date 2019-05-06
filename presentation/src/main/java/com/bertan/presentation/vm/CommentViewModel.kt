package com.bertan.presentation.vm

import com.bertan.domain.interactor.comment.AddComment
import com.bertan.domain.interactor.comment.GetComment
import com.bertan.presentation.mapper.CommentMapper.asCommentView
import com.bertan.presentation.mapper.CommentViewMapper.asComment
import com.bertan.presentation.model.CommentView
import java.util.*

class CommentViewModel(private val getCommentUseCase: GetComment, private val addCommentUseCase: AddComment) :
    RxUseCaseViewModel<Optional<CommentView>>(getCommentUseCase, addCommentUseCase) {
    override fun onCreateViewModel() = Unit

    fun getComment(postId: String, commentId: String) {
        postLoading()

        getCommentUseCase.execute(
            GetComment.Param(postId, commentId),
            onNext = { comment -> comment.map { it.asCommentView }.postSuccess() },
            onError = { it.postError("Failed to load Comment($postId, $commentId)!") }
        )
    }

    fun addComment(comment: CommentView) {
        postLoading()

        addCommentUseCase.execute(
            AddComment.Param(comment.asComment),
            onComplete = { Optional.of(comment).postSuccess() },
            onError = { it.postError("Failed to add $comment!") }
        )
    }
}