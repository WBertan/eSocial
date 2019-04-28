package com.bertan.domain.test

import com.bertan.domain.model.Account
import com.bertan.domain.model.Source
import java.util.*

abstract class DataFactory<T> {
    abstract fun get(): T
    fun get(count: Int): List<T> = List(count) { get() }

    fun randomString(): String = UUID.randomUUID().toString()
    fun randomBoolean(): Boolean = Math.random() < 0.5
    fun randomInt(bound: Int = Int.MAX_VALUE): Int = Random().nextInt(bound)
    fun randomLong(): Long = randomInt().toLong()
}

object SourceDataFactory : DataFactory<Source>() {
    override fun get(): Source =
        Source(
            randomString(),
            randomString(),
            randomString(),
            randomState(),
            randomColour()
        )

    private fun randomState() =
        if (randomBoolean()) {
            Source.State.Enabled
        } else {
            Source.State.Disabled
        }

    private fun randomColour() =
        Source.Colour.RGB(randomInt(255), randomInt(255), randomInt(255))
}

object AccountDataFactory : DataFactory<Account>() {
    override fun get(): Account =
        Account(
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