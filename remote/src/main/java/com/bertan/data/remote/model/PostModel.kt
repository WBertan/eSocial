package com.bertan.data.remote.model

import com.bertan.data.remote.randomDate
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostModel(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String,
    val createdDate: Long = randomDate()
)