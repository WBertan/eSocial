package com.bertan.data.model

data class SourceEntity(
    val id: String,
    val name: String,
    val icon: String?,
    val state: StateEntity,
    val colour: ColourEntity
) {
    sealed class StateEntity {
        object Enabled : StateEntity()
        object Disabled : StateEntity()
    }

    sealed class ColourEntity {
        data class RGB(val red: Int, val green: Int, val blue: Int) : ColourEntity()
        data class Hex(val value: String) : ColourEntity()
    }
}