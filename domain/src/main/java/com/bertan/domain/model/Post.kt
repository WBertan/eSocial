package com.bertan.domain.model

data class Post(
    val id: String,
    val title: String?,
    val bodyId: String?,
    val url: String?,
    val createdDate: Long
)