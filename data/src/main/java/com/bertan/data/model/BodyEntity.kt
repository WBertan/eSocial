package com.bertan.data.model

data class BodyEntity(
    val type: TypeEntity,
    val value: String
) {
    sealed class TypeEntity {
        object Text : TypeEntity()
        object Image : TypeEntity()
        object Video : TypeEntity()
    }
}