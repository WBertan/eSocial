package com.bertan.data.remote.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.remote.mapper.CommentMapper.asCommentEntity
import com.bertan.data.remote.test.CommentModelDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMapperSpec {
    @Test
    fun `given remoteModel when mapping to entity it should map`() {
        val remoteModel = CommentModelDataFactory.get()
        val expectedResult =
            CommentEntity(
                remoteModel.postId.toString(),
                remoteModel.id.toString(),
                BodyEntity(
                    BodyEntity.TypeEntity.Text,
                    remoteModel.body
                ),
                null,
                remoteModel.name,
                remoteModel.email,
                "https://api.adorable.io/avatars/200/${remoteModel.email}",
                remoteModel.createdDate
            )

        val result = remoteModel.asCommentEntity

        assertEquals(expectedResult, result)
    }
}