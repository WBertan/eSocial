package com.bertan.data.local.mapper

import com.bertan.data.local.mapper.PostEntityMapper.asPostModel
import com.bertan.data.local.mapper.PostModelMapper.asPostEntity
import com.bertan.data.local.model.PostModel
import com.bertan.data.local.test.PostEntityDataFactory
import com.bertan.data.local.test.PostModelDataFactory
import com.bertan.data.model.BodyEntity
import com.bertan.data.model.PostEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperSpec {
    @Test
    fun `given entity when mapping to model it should map`() {
        val entity = PostEntityDataFactory.get().copy(body = BodyEntity(BodyEntity.TypeEntity.Text, "dummyBody"))
        val expectedResult =
            PostModel(
                entity.id,
                entity.title,
                "text",
                "dummyBody",
                entity.url,
                entity.createdDate
            )

        val result = entity.asPostModel

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given model when mapping to entity it should map`() {
        val model = PostModelDataFactory.get().copy(bodyType = "text", bodyValue = "dummyBody")
        val expectedResult =
            PostEntity(
                model.id,
                model.title,
                BodyEntity(BodyEntity.TypeEntity.Text, "dummyBody"),
                model.url,
                model.createdDate
            )

        val result = model.asPostEntity

        assertEquals(expectedResult, result)
    }
}