package com.bertan.presentation.model

data class BodyView(
    val type: TypeView,
    val value: String
) {
    sealed class TypeView {
        object Text : TypeView()
        object Image : TypeView()
        object Video : TypeView()
    }
}