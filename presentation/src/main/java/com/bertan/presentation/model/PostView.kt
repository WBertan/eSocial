package com.bertan.presentation.model

data class PostView(
    val id: String,
    val title: String?,
    val body: BodyView?,
    val url: String?,
    val createdDate: Long
)