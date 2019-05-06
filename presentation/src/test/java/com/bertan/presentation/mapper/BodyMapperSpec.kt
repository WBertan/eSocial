package com.bertan.presentation.mapper

import com.bertan.domain.model.Body
import com.bertan.presentation.mapper.BodyMapper.asBodyView
import com.bertan.presentation.mapper.BodyViewMapper.asBody
import com.bertan.presentation.model.BodyView
import com.bertan.presentation.test.BodyDataFactory
import com.bertan.presentation.test.BodyViewDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class BodyMapperSpec {
    @Test
    fun `given domain when mapping to View it should map`() {
        val domain = BodyDataFactory.get().copy(type = Body.Type.Text)
        val expectedResult =
            BodyView(
                BodyView.TypeView.Text,
                domain.value
            )

        val result = domain.asBodyView

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given View when mapping to domain it should map`() {
        val view = BodyViewDataFactory.get().copy(type = BodyView.TypeView.Text)
        val expectedResult =
            Body(
                Body.Type.Text,
                view.value
            )

        val result = view.asBody

        assertEquals(expectedResult, result)
    }
}