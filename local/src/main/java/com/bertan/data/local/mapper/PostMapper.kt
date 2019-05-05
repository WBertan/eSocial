package com.bertan.data.local.mapper

import com.bertan.data.local.model.PostModel
import com.bertan.data.model.BodyEntity
import com.bertan.data.model.PostEntity

object PostModelMapper {
    val PostModel.asPostEntity: PostEntity
        get() =
            PostEntity(
                id,
                title,
                Pair(bodyType, bodyValue).asBodyEntity,
                url,
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

object PostEntityMapper {
    val PostEntity.asPostModel: PostModel
        get() =
            PostModel(
                id,
                title,
                body?.type?.asBodyModel,
                body?.value,
                url,
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