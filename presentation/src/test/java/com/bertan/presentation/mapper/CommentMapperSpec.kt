package com.bertan.presentation.mapper

import com.bertan.domain.model.Comment
import com.bertan.presentation.mapper.BodyMapper.asBodyView
import com.bertan.presentation.mapper.BodyViewMapper.asBody
import com.bertan.presentation.mapper.CommentMapper.asCommentView
import com.bertan.presentation.mapper.CommentViewMapper.asComment
import com.bertan.presentation.model.CommentView
import com.bertan.presentation.test.CommentDataFactory
import com.bertan.presentation.test.CommentViewDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMapperSpec {
    @Test
    fun `given domain when mapping to View it should map`() {
        val domain = CommentDataFactory.get()
        val expectedResult =
            CommentView(
                domain.postId,
                domain.id,
                domain.body!!.asBodyView,
                domain.url,
                domain.userName,
                domain.userEmail,
                domain.userIcon,
                domain.createdDate
            )

        val result = domain.asCommentView

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given View when mapping to domain it should map`() {
        val view = CommentViewDataFactory.get()
        val expectedResult =
            Comment(
                view.postId,
                view.id,
                view.body!!.asBody,
                view.url,
                view.userName,
                view.userEmail,
                view.userIcon,
                view.createdDate
            )

        val result = view.asComment

        assertEquals(expectedResult, result)
    }
}