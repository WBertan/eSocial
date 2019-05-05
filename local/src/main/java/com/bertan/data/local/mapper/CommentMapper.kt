package com.bertan.data.local.mapper

import com.bertan.data.local.model.CommentModel
import com.bertan.data.model.BodyEntity
import com.bertan.data.model.CommentEntity

object CommentModelMapper {
    val CommentModel.asCommentEntity: CommentEntity
        get() =
            CommentEntity(
                postId,
                id,
                Pair(bodyType, bodyValue).asBodyEntity,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )

    private val Pair<String?, String?>.asBodyEntity: BodyEntity?
        get() = letNotNull { bodyType, bodyValue -> bodyType.asTypeEntity?.let { BodyEntity(it, bodyValue) } }

    private val String.asTypeEntity: BodyEntity.TypeEntity?
        get() =
            when (this) {
                "image" ->
                    BodyEntity.TypeEntity.Image
                "text" ->
                    BodyEntity.TypeEntity.Text
                "video" ->
                    BodyEntity.TypeEntity.Video
                else ->
                    null
            }
}

object CommentEntityMapper {
    val CommentEntity.asCommentModel: CommentModel
        get() =
            CommentModel(
                postId,
                id,
                body?.type?.asBodyModel,
                body?.value,
                url,
                userName,
                userEmail,
                userIcon,
                createdDate
            )

    private val BodyEntity.TypeEntity.asBodyModel: String
        get() =
            when (this) {
                is BodyEntity.TypeEntity.Image ->
                    "image"
                is BodyEntity.TypeEntity.Text ->
                    "text"
                is BodyEntity.TypeEntity.Video ->
                    "video"
            }
}