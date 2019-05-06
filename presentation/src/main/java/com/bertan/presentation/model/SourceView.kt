package com.bertan.presentation.model

data class SourceView(
    val id: String,
    val name: String,
    val icon: String?,
    val state: StateView,
    val colour: ColourView
) {
    sealed class StateView {
        object Enabled : StateView()
        object Disabled : StateView()
    }

    sealed class ColourView {
        data class RGB(val red: Int, val green: Int, val blue: Int) : ColourView()
        data class Hex(val value: String) : ColourView()
    }
}