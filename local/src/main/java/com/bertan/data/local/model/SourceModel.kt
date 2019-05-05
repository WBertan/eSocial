package com.bertan.data.local.model

data class SourceModel(
    val id: String,
    val name: String,
    val icon: String?,
    val state: StateModel,
    val colour: ColourModel
) {
    sealed class StateModel {
        object Enabled : StateModel()
        object Disabled : StateModel()
    }

    sealed class ColourModel {
        data class RGB(val red: Int, val green: Int, val blue: Int) : ColourModel()
        data class Hex(val value: String) : ColourModel()
    }
}