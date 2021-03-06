package com.bertan.domain.model

data class Body(
    val type: Type,
    val value: String
) {
    sealed class Type {
        object Text : Type()
        object Image : Type()
        object Video : Type()
    }
}