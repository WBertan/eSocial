package com.bertan.data.local.model

data class BodyModel(
    val type: TypeEntity,
    val value: String
) {
    sealed class TypeEntity {
        object Text : TypeEntity()
        object Image : TypeEntity()
        object Video : TypeEntity()
    }
}