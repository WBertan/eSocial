package com.bertan.presentation.mapper

import com.bertan.domain.model.Body
import com.bertan.presentation.model.BodyView

object BodyViewMapper {
    val BodyView.asBody: Body
        get() =
            Body(
                type.asType,
                value
            )

    private val BodyView.TypeView.asType: Body.Type
        get() =
            when (this) {
                is BodyView.TypeView.Image ->
                    Body.Type.Image
                is BodyView.TypeView.Text ->
                    Body.Type.Text
                is BodyView.TypeView.Video ->
                    Body.Type.Video
            }
}

object BodyMapper {
    val Body.asBodyView: BodyView
        get() =
            BodyView(
                type.asTypeView,
                value
            )

    private val Body.Type.asTypeView: BodyView.TypeView
        get() =
            when (this) {
                is Body.Type.Image ->
                    BodyView.TypeView.Image
                is Body.Type.Text ->
                    BodyView.TypeView.Text
                is Body.Type.Video ->
                    BodyView.TypeView.Video
            }
}