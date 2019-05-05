package com.bertan.data.local.test

import com.bertan.data.local.model.AccountModel
import com.bertan.data.local.model.CommentModel
import com.bertan.data.local.model.PostModel
import com.bertan.data.local.model.SourceModel
import java.util.*

abstract class ModelDataFactory<T> {
    abstract fun get(): T
    fun get(count: Int): List<T> = List(count) { get() }

    fun randomString(): String = UUID.randomUUID().toString()
    fun randomBoolean(): Boolean = Math.random() < 0.5
    fun randomInt(bound: Int = Int.MAX_VALUE): Int = Random().nextInt(bound)
    fun randomLong(): Long = randomInt().toLong()
}

object SourceModelDataFactory : ModelDataFactory<SourceModel>() {
    override fun get(): SourceModel =
        SourceModel(
            randomString(),
            randomString(),
            randomString(),
            randomStateModel(),
            randomColourModel()
        )

    private fun randomStateModel() =
        listOf(
            SourceModel.StateModel.Enabled,
            SourceModel.StateModel.Disabled
        ).random()

    private fun randomColourModel() =
        listOf(
            SourceModel.ColourModel.RGB(randomInt(255), randomInt(255), randomInt(255)),
            SourceModel.ColourModel.Hex(randomString())
        ).random()
}

object AccountModelDataFactory : ModelDataFactory<AccountModel>() {
    override fun get(): AccountModel =
        AccountModel(
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

object PostModelDataFactory : ModelDataFactory<PostModel>() {
    override fun get(): PostModel =
        PostModel(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomLong()
        )
}

object CommentModelDataFactory : ModelDataFactory<CommentModel>() {
    override fun get(): CommentModel =
        CommentModel(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomLong()
        )
}