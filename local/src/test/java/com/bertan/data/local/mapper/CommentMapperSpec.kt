package com.bertan.data.local.mapper

import com.bertan.data.local.mapper.CommentEntityMapper.asCommentModel
import com.bertan.data.local.mapper.CommentModelMapper.asCommentEntity
import com.bertan.data.local.model.CommentModel
import com.bertan.data.local.test.CommentEntityDataFactory
import com.bertan.data.local.test.CommentModelDataFactory
import com.bertan.data.model.BodyEntity
import com.bertan.data.model.CommentEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMapperSpec {
    @Test
    fun `given entity when mapping to model it should map`() {
        val entity = CommentEntityDataFactory.get().copy(body = BodyEntity(BodyEntity.TypeEntity.Text, "dummyBody"))
        val expectedResult =
            CommentModel(
                entity.postId,
                entity.id,
                "text",
                "dummyBody",
                entity.url,
                entity.userName,
                entity.userEmail,
                entity.userIcon,
                entity.createdDate
            )

        val result = entity.asCommentModel

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given model when mapping to entity it should map`() {
        val model = CommentModelDataFactory.get().copy(bodyType = "text", bodyValue = "dummyBody")
        val expectedResult =
            CommentEntity(
                model.postId,
                model.id,
                BodyEntity(BodyEntity.TypeEntity.Text, "dummyBody"),
                model.url,
                model.userName,
                model.userEmail,
                model.userIcon,
                model.createdDate
            )

        val result = model.asCommentEntity

        assertEquals(expectedResult, result)
    }
}