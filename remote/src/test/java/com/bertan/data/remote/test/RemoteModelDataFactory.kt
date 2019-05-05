package com.bertan.data.remote.test

import com.bertan.data.remote.model.CommentModel
import com.bertan.data.remote.model.PostModel
import java.util.*

abstract class RemoteModelDataFactory<T> {
    abstract fun get(): T
    fun get(count: Int): List<T> = List(count) { get() }

    fun randomString(): String = UUID.randomUUID().toString()
    fun randomBoolean(): Boolean = Math.random() < 0.5
    fun randomInt(bound: Int = Int.MAX_VALUE): Int = Random().nextInt(bound)
    fun randomLong(): Long = randomInt().toLong()
}

object PostModelDataFactory : RemoteModelDataFactory<PostModel>() {
    override fun get(): PostModel =
        PostModel(
            randomLong(),
            randomLong(),
            randomString(),
            randomString(),
            randomLong()
        )
}

object CommentModelDataFactory : RemoteModelDataFactory<CommentModel>() {
    override fun get(): CommentModel =
        CommentModel(
            randomLong(),
            randomLong(),
            randomString(),
            randomString(),
            randomString(),
            randomLong()
        )
}