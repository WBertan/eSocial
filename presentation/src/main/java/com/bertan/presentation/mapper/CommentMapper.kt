package com.bertan.presentation.mapper

import com.bertan.domain.model.Comment
import com.bertan.presentation.mapper.BodyMapper.asBodyView
import com.bertan.presentation.mapper.BodyViewMapper.asBody
import com.bertan.presentation.model.CommentView

object CommentViewMapper {
    val CommentView.asComment: Comment
        get() =
            Comment(
                postId,
                id,
                body?.asBody,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )
}

object CommentMapper {
    val Comment.asCommentView: CommentView
        get() =
            CommentView(
                postId,
                id,
                body?.asBodyView,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )
}