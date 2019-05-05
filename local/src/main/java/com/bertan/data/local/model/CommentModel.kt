package com.bertan.data.local.model

import androidx.room.Entity
import com.bertan.data.local.db.Constants.Comments

@Entity(tableName = Comments.TABLE_NAME, primaryKeys = ["postId", "id"])
data class CommentModel(
    val postId: String,
    val id: String,
    val bodyType: String?,
    val bodyValue: String?,
    val url: String?,
    val userName: String?,
    val userEmail: String?,
    val userIcon: String?,
    val createdDate: Long
)