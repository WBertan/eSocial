package com.bertan.data.mapper

import com.bertan.data.mapper.BodyEntityMapper.asBody
import com.bertan.data.mapper.BodyMapper.asBodyEntity
import com.bertan.data.model.BodyEntity
import com.bertan.data.test.BodyDataFactory
import com.bertan.data.test.BodyEntityDataFactory
import com.bertan.domain.model.Body
import org.junit.Assert.assertEquals
import org.junit.Test

class BodyMapperSpec {
    @Test
    fun `given domain when mapping to entity it should map`() {
        val domain = BodyDataFactory.get().copy(
            type = Body.Type.Text
        )
        val expectedResult =
            BodyEntity(
                BodyEntity.TypeEntity.Text,
                domain.value
            )

        val result = domain.asBodyEntity

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given entity when mapping to domain it should map`() {
        val entity = BodyEntityDataFactory.get().copy(
            type = BodyEntity.TypeEntity.Text
        )
        val expectedResult =
            Body(
                Body.Type.Text,
                entity.value
            )

        val result = entity.asBody

        assertEquals(expectedResult, result)
    }
}