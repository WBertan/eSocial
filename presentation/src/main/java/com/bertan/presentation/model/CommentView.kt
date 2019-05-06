package com.bertan.presentation.model

data class CommentView(
    val postId: String,
    val id: String,
    val body: BodyView?,
    val url: String?,
    val userName: String?,
    val userEmail: String?,
    val userIcon: String?,
    val createdDate: Long
)