package com.bertan.presentation.mapper

import com.bertan.domain.model.Source
import com.bertan.presentation.mapper.SourceMapper.asSourceView
import com.bertan.presentation.mapper.SourceViewMapper.asSource
import com.bertan.presentation.model.SourceView
import com.bertan.presentation.test.SourceDataFactory
import com.bertan.presentation.test.SourceViewDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class SourceMapperSpec {
    @Test
    fun `given domain when mapping to View it should map`() {
        val domain = SourceDataFactory.get().copy(
            state = Source.State.Enabled,
            colour = Source.Colour.Hex("dummyHex")
        )
        val expectedResult =
            SourceView(
                domain.id,
                domain.name,
                domain.icon,
                SourceView.StateView.Enabled,
                SourceView.ColourView.Hex("dummyHex")
            )

        val result = domain.asSourceView

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given View when mapping to domain it should map`() {
        val view = SourceViewDataFactory.get().copy(
            state = SourceView.StateView.Enabled,
            colour = SourceView.ColourView.Hex("dummyHex")
        )
        val expectedResult =
            Source(
                view.id,
                view.name,
                view.icon,
                Source.State.Enabled,
                Source.Colour.Hex("dummyHex")
            )

        val result = view.asSource

        assertEquals(expectedResult, result)
    }
}