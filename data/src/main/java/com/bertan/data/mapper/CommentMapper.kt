package com.bertan.data.mapper

import com.bertan.data.model.CommentEntity
import com.bertan.domain.model.Comment

object CommentEntityMapper {
    val CommentEntity.asComment: Comment
        get() =
            Comment(
                accountId,
                postId,
                id,
                bodyId,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )
}

object CommentMapper {
    val Comment.asCommentEntity: CommentEntity
        get() =
            CommentEntity(
                accountId,
                postId,
                id,
                bodyId,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )
}