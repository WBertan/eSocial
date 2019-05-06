package com.bertan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bertan.data.local.db.Constants.Comments
import com.bertan.data.local.model.CommentModel
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: CommentModel)

    @Query("SELECT * FROM ${Comments.TABLE_NAME} WHERE postId = :postId")
    fun commentsByPost(postId: String): Flowable<List<CommentModel>>

    @Query("SELECT * FROM ${Comments.TABLE_NAME} WHERE postId = :postId AND id = :commentId")
    fun comment(postId: String, commentId: String): Single<CommentModel>
}