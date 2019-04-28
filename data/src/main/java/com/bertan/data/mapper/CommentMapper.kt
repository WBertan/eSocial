package com.bertan.data.mapper

import com.bertan.data.model.CommentEntity
import com.bertan.domain.model.Comment

object CommentEntityMapper {
    val CommentEntity?.asComment: Comment?
        get() = mapTo {
            Comment(
                it.accountId,
                it.postId,
                it.id,
                it.bodyId,
                it.url,
                it.userName,
                it.userEmail,
                it.userIcon,
                it.createdDate
            )
        }
}

object CommentMapper {
    val Comment?.asCommentEntity: CommentEntity?
        get() = mapTo {
            CommentEntity(
                it.accountId,
                it.postId,
                it.id,
                it.bodyId,
                it.url,
                it.userName,
                it.userEmail,
                it.userIcon,
                it.createdDate
            )
        }
}