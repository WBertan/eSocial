package com.bertan.data.test

import com.bertan.data.model.*
import java.util.*

abstract class EntityDataFactory<T> {
    abstract fun get(): T
    fun get(count: Int): List<T> = List(count) { get() }

    fun randomString(): String = UUID.randomUUID().toString()
    fun randomBoolean(): Boolean = Math.random() < 0.5
    fun randomInt(bound: Int = Int.MAX_VALUE): Int = Random().nextInt(bound)
    fun randomLong(): Long = randomInt().toLong()
}

object SourceEntityDataFactory : EntityDataFactory<SourceEntity>() {
    override fun get(): SourceEntity =
        SourceEntity(
            randomString(),
            randomString(),
            randomString(),
            randomStateEntity(),
            randomColourEntity()
        )

    private fun randomStateEntity() =
        listOf(
            SourceEntity.StateEntity.Enabled,
            SourceEntity.StateEntity.Disabled
        ).random()

    private fun randomColourEntity() =
        listOf(
            SourceEntity.ColourEntity.RGB(randomInt(255), randomInt(255), randomInt(255)),
            SourceEntity.ColourEntity.Hex(randomString())
        ).random()
}

object AccountEntityDataFactory : EntityDataFactory<AccountEntity>() {
    override fun get(): AccountEntity =
        AccountEntity(
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

object PostEntityDataFactory : EntityDataFactory<PostEntity>() {
    override fun get(): PostEntity =
        PostEntity(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomLong()
        )
}

object CommentEntityDataFactory : EntityDataFactory<CommentEntity>() {
    override fun get(): CommentEntity =
        CommentEntity(
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

object BodyEntityDataFactory : EntityDataFactory<BodyEntity>() {
    override fun get(): BodyEntity =
        BodyEntity(
            randomString(),
            randomString(),
            randomTypeEntity(),
            randomString()
        )

    private fun randomTypeEntity() =
        listOf(
            BodyEntity.TypeEntity.Image,
            BodyEntity.TypeEntity.Text,
            BodyEntity.TypeEntity.Video
        ).random()
}