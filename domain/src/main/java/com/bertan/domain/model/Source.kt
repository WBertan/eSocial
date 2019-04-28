package com.bertan.domain.model

data class Source(
    val id: String,
    val name: String,
    val icon: String?,
    val state: State,
    val colour: Colour
) {
    sealed class State {
        object Enabled : State()
        object Disabled : State()
    }

    sealed class Colour {
        data class RGB(val red: Int, val green: Int, val blue: Int) : Colour()
        data class Hex(val value: String) : Colour()
    }
}