package com.bertan.esocial.test

import com.bertan.presentation.model.SourceView
import java.util.*

abstract class ViewDataFactory<T> {
    abstract fun get(): T
    fun get(count: Int): List<T> = List(count) { get() }

    fun randomString(): String = UUID.randomUUID().toString()
    fun randomBoolean(): Boolean = Math.random() < 0.5
    fun randomInt(bound: Int = Int.MAX_VALUE): Int = Random().nextInt(bound)
    fun randomLong(): Long = randomInt().toLong()
    fun randomHex(): String =
        listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F").random()
}

object SourceViewDataFactory : ViewDataFactory<SourceView>() {
    override fun get(): SourceView =
        SourceView(
            randomString(),
            randomString(),
            randomString(),
            randomStateView(),
            randomColourView()
        )

    private fun randomStateView() =
        listOf(
            SourceView.StateView.Enabled,
            SourceView.StateView.Disabled
        ).random()

    private fun randomColourView() =
        listOf(
            SourceView.ColourView.RGB(randomInt(255), randomInt(255), randomInt(255)),
            SourceView.ColourView.Hex("#${List(6) { randomHex() }.joinToString("")}")
        ).random()
}