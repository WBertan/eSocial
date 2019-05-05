package com.bertan.data.model

data class CommentEntity(
    val postId: String,
    val id: String,
    val body: BodyEntity?,
    val url: String?,
    val userName: String?,
    val userEmail: String?,
    val userIcon: String?,
    val createdDate: Long
)