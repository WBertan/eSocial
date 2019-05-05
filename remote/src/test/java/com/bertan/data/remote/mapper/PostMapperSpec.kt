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
            Pair(
                PostEntity(
                    remoteModel.id.toString(),
                    remoteModel.title,
                    "post_${remoteModel.id}",
                    "https://jsonplaceholder.typicode.com/posts/${remoteModel.id}",
                    0
                ),
                BodyEntity(
                    "post_${remoteModel.id}",
                    BodyEntity.TypeEntity.Text,
                    remoteModel.body
                )
            )

        val result = remoteModel.asPostEntity

        assertEquals(expectedResult, result.copy(result.first.copy(createdDate = 0), result.second))
    }
}