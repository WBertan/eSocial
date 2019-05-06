package com.bertan.presentation.test

import com.bertan.presentation.model.*
import java.util.*

abstract class ViewDataFactory<T> {
    abstract fun get(): T
    fun get(count: Int): List<T> = List(count) { get() }

    fun randomString(): String = UUID.randomUUID().toString()
    fun randomBoolean(): Boolean = Math.random() < 0.5
    fun randomInt(bound: Int = Int.MAX_VALUE): Int = Random().nextInt(bound)
    fun randomLong(): Long = randomInt().toLong()
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
            SourceView.ColourView.Hex(randomString())
        ).random()
}

object AccountViewDataFactory : ViewDataFactory<AccountView>() {
    override fun get(): AccountView =
        AccountView(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomLong(),
            randomLong()
        )
}

object PostViewDataFactory : ViewDataFactory<PostView>() {
    override fun get(): PostView =
        PostView(
            randomString(),
            randomString(),
            BodyViewDataFactory.get(),
            randomString(),
            randomLong()
        )
}

object CommentViewDataFactory : ViewDataFactory<CommentView>() {
    override fun get(): CommentView =
        CommentView(
            randomString(),
            randomString(),
            BodyViewDataFactory.get(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomLong()
        )
}

object BodyViewDataFactory : ViewDataFactory<BodyView>() {
    override fun get(): BodyView =
        BodyView(
            randomTypeView(),
            randomString()
        )

    private fun randomTypeView() =
        listOf(
            BodyView.TypeView.Image,
            BodyView.TypeView.Text,
            BodyView.TypeView.Video
        ).random()
}