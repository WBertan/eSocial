package com.bertan.data.mapper

import com.bertan.data.mapper.BodyEntityMapper.asBody
import com.bertan.data.mapper.BodyMapper.asBodyEntity
import com.bertan.data.model.CommentEntity
import com.bertan.domain.model.Comment

object CommentEntityMapper {
    val CommentEntity.asComment: Comment
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
    val Comment.asCommentEntity: CommentEntity
        get() =
            CommentEntity(
                postId,
                id,
                body?.asBodyEntity,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )
}