package com.bertan.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bertan.data.local.db.Constants.Comments

@Entity(tableName = Comments.TABLE_NAME)
data class CommentModel(
    @PrimaryKey val postId: String,
    @PrimaryKey val id: String,
    val body: BodyModel?,
    val url: String?,
    val userName: String?,
    val userEmail: String?,
    val userIcon: String?,
    val createdDate: Long
)