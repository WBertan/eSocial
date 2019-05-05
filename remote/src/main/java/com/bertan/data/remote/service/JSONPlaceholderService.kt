package com.bertan.data.remote.service

import com.bertan.data.remote.model.CommentModel
import com.bertan.data.remote.model.PostModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JSONPlaceholderService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts")
    fun getPosts(): Observable<List<PostModel>>

    @GET("posts")
    fun getPostsByUser(@Query("userId") userId: Long): Observable<List<PostModel>>

    @GET("posts/{postId}")
    fun getPost(@Path("postId") postId: Long): Observable<PostModel>

    @GET("comments")
    fun getCommentsByPost(@Query("postId") postId: Long): Observable<List<CommentModel>>

    @GET("comments/{postId}")
    fun getComment(@Path("commentId") commentId: Long): Observable<CommentModel>
}