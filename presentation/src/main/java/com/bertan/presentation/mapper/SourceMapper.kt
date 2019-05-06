package com.bertan.presentation.mapper

import com.bertan.domain.model.Source
import com.bertan.presentation.model.SourceView

object SourceViewMapper {
    val SourceView.asSource: Source
        get() =
            Source(
                id,
                name,
                icon,
                requireNotNull(state.asState),
                requireNotNull(colour.asColour)
            )

    private val SourceView.StateView.asState: Source.State
        get() =
            when (this) {
                is SourceView.StateView.Enabled ->
                    Source.State.Enabled
                is SourceView.StateView.Disabled ->
                    Source.State.Disabled
            }

    private val SourceView.ColourView.asColour: Source.Colour
        get() =
            when (this) {
                is SourceView.ColourView.RGB ->
                    Source.Colour.RGB(red, green, blue)
                is SourceView.ColourView.Hex ->
                    Source.Colour.Hex(value)
            }
}

object SourceMapper {
    val Source.asSourceView: SourceView
        get() =
            SourceView(
                id,
                name,
                icon,
                requireNotNull(state.asStateView),
                requireNotNull(colour.asColourView)
            )

    private val Source.State.asStateView: SourceView.StateView
        get() =
            when (this) {
                is Source.State.Enabled ->
                    SourceView.StateView.Enabled
                is Source.State.Disabled ->
                    SourceView.StateView.Disabled
            }

    private val Source.Colour.asColourView: SourceView.ColourView
        get() =
            when (this) {
                is Source.Colour.RGB ->
                    SourceView.ColourView.RGB(red, green, blue)
                is Source.Colour.Hex ->
                    SourceView.ColourView.Hex(value)
            }
}