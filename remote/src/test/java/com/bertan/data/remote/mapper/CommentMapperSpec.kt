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
            Pair(
                CommentEntity(
                    remoteModel.postId.toString(),
                    remoteModel.id.toString(),
                    "comment_${remoteModel.postId}_${remoteModel.id}",
                    null,
                    remoteModel.name,
                    remoteModel.email,
                    "https://api.adorable.io/avatars/200/${remoteModel.email}",
                    0
                ),
                BodyEntity(
                    "comment_${remoteModel.postId}_${remoteModel.id}",
                    BodyEntity.TypeEntity.Text,
                    remoteModel.body
                )
            )

        val result = remoteModel.asCommentEntity

        assertEquals(expectedResult, result.copy(result.first.copy(createdDate = 0), result.second))
    }
}