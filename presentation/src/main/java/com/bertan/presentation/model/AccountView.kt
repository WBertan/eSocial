package com.bertan.presentation.model

data class AccountView(
    val sourceId: String,
    val id: String,
    val name: String?,
    val icon: String?,
    val userName: String?,
    val url: String?,
    val createdDate: Long,
    val lastSyncDate: Long
)