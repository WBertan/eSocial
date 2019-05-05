package com.bertan.data.mapper

import com.bertan.data.mapper.BodyEntityMapper.asBody
import com.bertan.data.mapper.BodyMapper.asBodyEntity
import com.bertan.data.mapper.PostEntityMapper.asPost
import com.bertan.data.mapper.PostMapper.asPostEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.test.PostDataFactory
import com.bertan.data.test.PostEntityDataFactory
import com.bertan.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperSpec {
    @Test
    fun `given domain when mapping to entity it should map`() {
        val domain = PostDataFactory.get()
        val expectedResult =
            PostEntity(
                domain.id,
                domain.title,
                domain.body!!.asBodyEntity,
                domain.url,
                domain.createdDate
            )

        val result = domain.asPostEntity

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given entity when mapping to domain it should map`() {
        val entity = PostEntityDataFactory.get()
        val expectedResult =
            Post(
                entity.id,
                entity.title,
                entity.body!!.asBody,
                entity.url,
                entity.createdDate
            )

        val result = entity.asPost

        assertEquals(expectedResult, result)
    }
}