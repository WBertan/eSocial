package com.bertan.esocial.extension

import android.content.res.ColorStateList
import android.graphics.Color
import com.bertan.presentation.model.SourceView

val SourceView.ColourView.toColorStateList: ColorStateList
    get() =
        when (this) {
            is SourceView.ColourView.RGB -> {
                val (red, green, blue) = this
                Color.rgb(red, green, blue)
            }
            is SourceView.ColourView.Hex ->
                Color.parseColor(this.value)
        }.let(ColorStateList::valueOf)

val SourceView.StateView.asBoolean: Boolean
    get() =
        when (this) {
            is SourceView.StateView.Enabled -> true
            is SourceView.StateView.Disabled -> false
        }