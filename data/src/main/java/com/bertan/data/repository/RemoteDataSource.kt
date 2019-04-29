package com.bertan.data.repository

import com.bertan.data.model.CommentEntity
import com.bertan.data.model.PostEntity
import io.reactivex.Observable
import java.util.*

interface RemoteDataSource {
    fun getPosts(): Observable<List<PostEntity>>
    fun getPostsByAccount(accountId: String): Observable<List<PostEntity>>
    fun getPost(accountId: String, postId: String): Observable<Optional<PostEntity>>

    fun getCommentsByPost(accountId: String, postId: String): Observable<List<CommentEntity>>
    fun getComment(accountId: String, postId: String, commentId: String): Observable<Optional<CommentEntity>>
}