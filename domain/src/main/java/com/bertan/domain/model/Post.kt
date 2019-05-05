package com.bertan.domain.model

data class Post(
    val id: String,
    val title: String?,
    val body: Body?,
    val url: String?,
    val createdDate: Long
)