package com.bertan.data.remote.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.remote.model.CommentModel

object CommentMapper {
    val CommentModel.asCommentEntity: CommentEntity
        get() =
            CommentEntity(
                postId.toString(),
                id.toString(),
                BodyEntity(
                    BodyEntity.TypeEntity.Text,
                    body
                ),
                null,
                name,
                email,
                "https://api.adorable.io/avatars/200/$email",
                createdDate
            )
}