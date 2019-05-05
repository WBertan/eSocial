package com.bertan.data.local.mapper

import com.bertan.data.local.model.SourceModel
import com.bertan.data.model.SourceEntity

object SourceModelMapper {
    val SourceModel.asSourceEntity: SourceEntity
        get() =
            SourceEntity(
                id,
                name,
                icon,
                state.asStateEntity,
                colour.asColourEntity
            )

    private val SourceModel.StateModel.asStateEntity: SourceEntity.StateEntity
        get() =
            when (this) {
                is SourceModel.StateModel.Enabled ->
                    SourceEntity.StateEntity.Enabled
                is SourceModel.StateModel.Disabled ->
                    SourceEntity.StateEntity.Disabled
            }

    private val SourceModel.ColourModel.asColourEntity: SourceEntity.ColourEntity
        get() =
            when (this) {
                is SourceModel.ColourModel.RGB ->
                    SourceEntity.ColourEntity.RGB(red, green, blue)
                is SourceModel.ColourModel.Hex ->
                    SourceEntity.ColourEntity.Hex(value)
            }
}

object SourceEntityMapper {
    val SourceEntity.asSourceModel: SourceModel
        get() =
            SourceModel(
                id,
                name,
                icon,
                requireNotNull(state.asStateModel),
                requireNotNull(colour.asColourModel)
            )

    private val SourceEntity.StateEntity.asStateModel: SourceModel.StateModel
        get() =
            when (this) {
                is SourceEntity.StateEntity.Enabled ->
                    SourceModel.StateModel.Enabled
                is SourceEntity.StateEntity.Disabled ->
                    SourceModel.StateModel.Disabled
            }

    private val SourceEntity.ColourEntity.asColourModel: SourceModel.ColourModel
        get() =
            when (this) {
                is SourceEntity.ColourEntity.RGB ->
                    SourceModel.ColourModel.RGB(red, green, blue)
                is SourceEntity.ColourEntity.Hex ->
                    SourceModel.ColourModel.Hex(value)
            }
}