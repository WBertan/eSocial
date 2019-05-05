package com.bertan.data.model

data class PostEntity(
    val id: String,
    val title: String?,
    val bodyId: String?,
    val url: String?,
    val createdDate: Long
)