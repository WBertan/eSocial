package com.bertan.data.model

data class CommentEntity(
    val accountId: String,
    val postId: String,
    val id: String,
    val bodyId: String?,
    val url: String?,
    val userName: String?,
    val userEmail: String?,
    val userIcon: String?,
    val createdDate: Long
)