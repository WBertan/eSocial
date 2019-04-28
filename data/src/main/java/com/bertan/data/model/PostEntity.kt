package com.bertan.data.model

data class PostEntity(
    val accountId: String,
    val id: String,
    val title: String?,
    val bodyId: String?,
    val url: String?,
    val createdDate: Long
)