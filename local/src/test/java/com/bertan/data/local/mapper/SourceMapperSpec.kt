package com.bertan.data.local.mapper

import com.bertan.data.local.mapper.SourceEntityMapper.asSourceModel
import com.bertan.data.local.mapper.SourceModelMapper.asSourceEntity
import com.bertan.data.local.model.SourceModel
import com.bertan.data.local.test.SourceEntityDataFactory
import com.bertan.data.local.test.SourceModelDataFactory
import com.bertan.data.model.SourceEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class SourceMapperSpec {
    @Test
    fun `given entity when mapping to model it should map`() {
        val entity = SourceEntityDataFactory.get().copy(
            state = SourceEntity.StateEntity.Enabled,
            colour = SourceEntity.ColourEntity.Hex("dummyHex")
        )
        val expectedResult =
            SourceModel(
                entity.id,
                entity.name,
                entity.icon,
                SourceModel.StateModel.Enabled,
                SourceModel.ColourModel.Hex("dummyHex")
            )

        val result = entity.asSourceModel

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given model when mapping to entity it should map`() {
        val model = SourceModelDataFactory.get().copy(
            state = SourceModel.StateModel.Enabled,
            colour = SourceModel.ColourModel.Hex("dummyHex")
        )
        val expectedResult =
            SourceEntity(
                model.id,
                model.name,
                model.icon,
                SourceEntity.StateEntity.Enabled,
                SourceEntity.ColourEntity.Hex("dummyHex")
            )

        val result = model.asSourceEntity

        assertEquals(expectedResult, result)
    }
}