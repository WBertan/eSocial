package com.bertan.data.remote.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.remote.mapper.PostMapper.asPostEntity
import com.bertan.data.remote.test.PostModelDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperSpec {
    @Test
    fun `given remoteModel when mapping to entity it should map`() {
        val remoteModel = PostModelDataFactory.get()
        val expectedResult =
            PostEntity(
                remoteModel.id.toString(),
                remoteModel.title,
                BodyEntity(
                    BodyEntity.TypeEntity.Text,
                    remoteModel.body
                ),
                "https://jsonplaceholder.typicode.com/posts/${remoteModel.id}",
                remoteModel.createdDate
            )

        val result = remoteModel.asPostEntity

        assertEquals(expectedResult, result)
    }
}