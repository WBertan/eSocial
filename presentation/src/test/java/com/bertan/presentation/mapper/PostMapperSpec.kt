package com.bertan.presentation.mapper

import com.bertan.domain.model.Post
import com.bertan.presentation.mapper.BodyMapper.asBodyView
import com.bertan.presentation.mapper.BodyViewMapper.asBody
import com.bertan.presentation.mapper.PostMapper.asPostView
import com.bertan.presentation.mapper.PostViewMapper.asPost
import com.bertan.presentation.model.PostView
import com.bertan.presentation.test.PostDataFactory
import com.bertan.presentation.test.PostViewDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperSpec {
    @Test
    fun `given domain when mapping to View it should map`() {
        val domain = PostDataFactory.get()
        val expectedResult =
            PostView(
                domain.id,
                domain.title,
                domain.body!!.asBodyView,
                domain.url,
                domain.createdDate
            )

        val result = domain.asPostView

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given View when mapping to domain it should map`() {
        val view = PostViewDataFactory.get()
        val expectedResult =
            Post(
                view.id,
                view.title,
                view.body!!.asBody,
                view.url,
                view.createdDate
            )

        val result = view.asPost

        assertEquals(expectedResult, result)
    }
}