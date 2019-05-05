package com.bertan.data.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.domain.model.Body

object BodyEntityMapper {
    val BodyEntity.asBody: Body
        get() =
            Body(
                requireNotNull(type.asType),
                value
            )

    private val BodyEntity.TypeEntity.asType: Body.Type
        get() =
            when (this) {
                is BodyEntity.TypeEntity.Image ->
                    Body.Type.Image
                is BodyEntity.TypeEntity.Text ->
                    Body.Type.Text
                is BodyEntity.TypeEntity.Video ->
                    Body.Type.Video
            }
}

object BodyMapper {
    val Body.asBodyEntity: BodyEntity
        get() =
            BodyEntity(
                requireNotNull(type.asTypeEntity),
                value
            )

    private val Body.Type.asTypeEntity: BodyEntity.TypeEntity
        get() =
            when (this) {
                is Body.Type.Image ->
                    BodyEntity.TypeEntity.Image
                is Body.Type.Text ->
                    BodyEntity.TypeEntity.Text
                is Body.Type.Video ->
                    BodyEntity.TypeEntity.Video
            }
}