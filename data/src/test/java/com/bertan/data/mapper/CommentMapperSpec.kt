package com.bertan.data.mapper

import com.bertan.data.mapper.CommentEntityMapper.asComment
import com.bertan.data.mapper.CommentMapper.asCommentEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.test.CommentDataFactory
import com.bertan.data.test.CommentEntityDataFactory
import com.bertan.domain.model.Comment
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMapperSpec {
    @Test
    fun `given domain when mapping to entity it should map`() {
        val domain = CommentDataFactory.get()
        val expectedResult =
            CommentEntity(
                domain.accountId,
                domain.postId,
                domain.id,
                domain.bodyId,
                domain.url,
                domain.userName,
                domain.userEmail,
                domain.userIcon,
                domain.createdDate
            )

        val result = domain.asCommentEntity

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given entity when mapping to domain it should map`() {
        val entity = CommentEntityDataFactory.get()
        val expectedResult =
            Comment(
                entity.accountId,
                entity.postId,
                entity.id,
                entity.bodyId,
                entity.url,
                entity.userName,
                entity.userEmail,
                entity.userIcon,
                entity.createdDate
            )

        val result = entity.asComment

        assertEquals(expectedResult, result)
    }
}