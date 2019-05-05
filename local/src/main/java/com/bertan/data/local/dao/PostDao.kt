package com.bertan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bertan.data.local.db.Constants.Posts
import com.bertan.data.local.model.PostModel
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostModel)

    @Query("SELECT * FROM ${Posts.TABLE_NAME}")
    fun posts(): Flowable<List<PostModel>>

    @Query("SELECT * FROM ${Posts.TABLE_NAME} WHERE id = :postId")
    fun post(postId: String): Single<PostModel>
}