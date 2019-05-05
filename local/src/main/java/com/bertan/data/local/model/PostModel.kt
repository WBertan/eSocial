package com.bertan.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bertan.data.local.db.Constants.Posts

@Entity(tableName = Posts.TABLE_NAME)
data class PostModel(
    @PrimaryKey val id: String,
    val title: String?,
    val bodyType: String?,
    val bodyValue: String?,
    val url: String?,
    val createdDate: Long
)