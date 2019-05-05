package com.bertan.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentModel(
    val postId: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)