package com.bertan.data.mapper

import com.bertan.data.model.SourceEntity
import com.bertan.domain.model.Source

object SourceEntityMapper {
    val SourceEntity?.asSource: Source?
        get() = mapTo {
            Source(
                it.id,
                it.name,
                it.icon,
                requireNotNull(it.state.asState),
                requireNotNull(it.colour.asColour)
            )
        }

    private val SourceEntity.StateEntity?.asState: Source.State?
        get() = mapTo {
            when (it) {
                is SourceEntity.StateEntity.Enabled ->
                    Source.State.Enabled
                is SourceEntity.StateEntity.Disabled ->
                    Source.State.Disabled
            }
        }

    private val SourceEntity.ColourEntity?.asColour: Source.Colour?
        get() = mapTo {
            when (it) {
                is SourceEntity.ColourEntity.RGB ->
                    Source.Colour.RGB(it.red, it.green, it.blue)
                is SourceEntity.ColourEntity.Hex ->
                    Source.Colour.Hex(it.value)
            }
        }
}

object SourceMapper {
    val Source?.asSourceEntity: SourceEntity?
        get() = mapTo {
            SourceEntity(
                it.id,
                it.name,
                it.icon,
                requireNotNull(it.state.asStateEntity),
                requireNotNull(it.colour.asColourEntity)
            )
        }

    private val Source.State?.asStateEntity: SourceEntity.StateEntity?
        get() = mapTo {
            when (it) {
                is Source.State.Enabled ->
                    SourceEntity.StateEntity.Enabled
                is Source.State.Disabled ->
                    SourceEntity.StateEntity.Disabled
            }
        }

    private val Source.Colour?.asColourEntity: SourceEntity.ColourEntity?
        get() = mapTo {
            when (it) {
                is Source.Colour.RGB ->
                    SourceEntity.ColourEntity.RGB(it.red, it.green, it.blue)
                is Source.Colour.Hex ->
                    SourceEntity.ColourEntity.Hex(it.value)
            }
        }
}