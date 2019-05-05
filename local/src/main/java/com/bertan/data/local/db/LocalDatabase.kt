package com.bertan.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bertan.data.local.dao.AccountDao
import com.bertan.data.local.dao.CommentDao
import com.bertan.data.local.dao.PostDao
import com.bertan.data.local.model.AccountModel
import com.bertan.data.local.model.CommentModel
import com.bertan.data.local.model.PostModel

@Database(
    entities = [
        AccountModel::class,
        PostModel::class,
        CommentModel::class
    ],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val accountDao: AccountDao
    abstract val postDao: PostDao
    abstract val commentDao: CommentDao
}