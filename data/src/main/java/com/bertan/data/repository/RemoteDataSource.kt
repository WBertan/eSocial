package com.bertan.data.repository

import com.bertan.data.model.CommentEntity
import com.bertan.data.model.PostEntity
import io.reactivex.Observable
import java.util.*

interface RemoteDataSource {
    fun getPosts(): Observable<List<PostEntity>>
    fun getPost(postId: String): Observable<Optional<PostEntity>>

    fun getCommentsByPost(postId: String): Observable<List<CommentEntity>>
    fun getComment(postId: String, commentId: String): Observable<Optional<CommentEntity>>
}