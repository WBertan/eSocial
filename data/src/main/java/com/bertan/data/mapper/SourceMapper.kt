package com.bertan.data.mapper

import com.bertan.data.model.SourceEntity
import com.bertan.domain.model.Source

object SourceEntityMapper {
    val SourceEntity.asSource: Source
        get() =
            Source(
                id,
                name,
                icon,
                requireNotNull(state.asState),
                requireNotNull(colour.asColour)
            )

    private val SourceEntity.StateEntity.asState: Source.State
        get() =
            when (this) {
                is SourceEntity.StateEntity.Enabled ->
                    Source.State.Enabled
                is SourceEntity.StateEntity.Disabled ->
                    Source.State.Disabled
            }

    private val SourceEntity.ColourEntity.asColour: Source.Colour
        get() =
            when (this) {
                is SourceEntity.ColourEntity.RGB ->
                    Source.Colour.RGB(red, green, blue)
                is SourceEntity.ColourEntity.Hex ->
                    Source.Colour.Hex(value)
            }
}

object SourceMapper {
    val Source.asSourceEntity: SourceEntity
        get() =
            SourceEntity(
                id,
                name,
                icon,
                requireNotNull(state.asStateEntity),
                requireNotNull(colour.asColourEntity)
            )

    private val Source.State.asStateEntity: SourceEntity.StateEntity
        get() =
            when (this) {
                is Source.State.Enabled ->
                    SourceEntity.StateEntity.Enabled
                is Source.State.Disabled ->
                    SourceEntity.StateEntity.Disabled
            }

    private val Source.Colour.asColourEntity: SourceEntity.ColourEntity
        get() =
            when (this) {
                is Source.Colour.RGB ->
                    SourceEntity.ColourEntity.RGB(red, green, blue)
                is Source.Colour.Hex ->
                    SourceEntity.ColourEntity.Hex(value)
            }
}