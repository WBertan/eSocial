package com.bertan.data.mapper

import com.bertan.data.mapper.SourceEntityMapper.asSource
import com.bertan.data.mapper.SourceMapper.asSourceEntity
import com.bertan.data.model.SourceEntity
import com.bertan.data.test.SourceDataFactory
import com.bertan.data.test.SourceEntityDataFactory
import com.bertan.domain.model.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class SourceMapperSpec {
    @Test
    fun `given domain when mapping to entity it should map`() {
        val domain = SourceDataFactory.get().copy(
            state = Source.State.Enabled,
            colour = Source.Colour.Hex("dummyHex")
        )
        val expectedResult =
            SourceEntity(
                domain.id,
                domain.name,
                domain.icon,
                SourceEntity.StateEntity.Enabled,
                SourceEntity.ColourEntity.Hex("dummyHex")
            )

        val result = domain.asSourceEntity

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given entity when mapping to domain it should map`() {
        val entity = SourceEntityDataFactory.get().copy(
            state = SourceEntity.StateEntity.Enabled,
            colour = SourceEntity.ColourEntity.Hex("dummyHex")
        )
        val expectedResult =
            Source(
                entity.id,
                entity.name,
                entity.icon,
                Source.State.Enabled,
                Source.Colour.Hex("dummyHex")
            )

        val result = entity.asSource

        assertEquals(expectedResult, result)
    }
}