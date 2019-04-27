package com.bertan.domain.model

data class Post(
    val accountId: String,
    val id: String,
    val title: String?,
    val bodyId: String?,
    val url: String?,
    val createdDate: Long
)