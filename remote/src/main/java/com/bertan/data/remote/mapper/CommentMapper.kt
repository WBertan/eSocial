package com.bertan.data.remote.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.remote.model.CommentModel
import com.bertan.data.remote.randomDate

object CommentMapper {
    val CommentModel.asCommentEntity: Pair<CommentEntity, BodyEntity>
        get() =
            Pair(
                CommentEntity(
                    postId.toString(),
                    id.toString(),
                    "comment_${postId}_$id",
                    null,
                    name,
                    email,
                    "https://api.adorable.io/avatars/200/$email",
                    randomDate()
                ),
                BodyEntity(
                    "comment_${postId}_$id",
                    BodyEntity.TypeEntity.Text,
                    body
                )
            )
}