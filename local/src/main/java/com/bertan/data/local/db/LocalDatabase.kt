package com.bertan.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    class Factory(context: Context) : () -> LocalDatabase {
        private val database: LocalDatabase by lazy {
            Room
                .databaseBuilder(context.applicationContext, LocalDatabase::class.java, "localDatabase")
                .build()
        }

        override fun invoke(): LocalDatabase = database
    }

    abstract val accountDao: AccountDao
    abstract val postDao: PostDao
    abstract val commentDao: CommentDao
}