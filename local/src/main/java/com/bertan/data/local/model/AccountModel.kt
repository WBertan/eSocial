package com.bertan.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bertan.data.local.db.Constants.Accounts

@Entity(tableName = Accounts.TABLE_NAME)
data class AccountModel(
    val sourceId: String,
    @PrimaryKey val id: String,
    val name: String?,
    val icon: String?,
    val userName: String?,
    val url: String?,
    val createdDate: Long,
    val lastSyncDate: Long
)