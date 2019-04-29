package com.bertan.data.repository

import com.bertan.data.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

interface LocalDataSource {
    fun getSources(): Observable<List<SourceEntity>>

    fun getAccounts(): Observable<List<AccountEntity>>
    fun getAccount(accountId: String): Observable<Optional<AccountEntity>>
    fun addAccount(account: AccountEntity): Completable

    fun getPosts(): Observable<List<PostEntity>>
    fun getPostsByAccount(accountId: String): Observable<List<PostEntity>>
    fun getPost(accountId: String, postId: String): Observable<Optional<PostEntity>>
    fun addPost(post: PostEntity): Completable

    fun getCommentsByPost(accountId: String, postId: String): Observable<List<CommentEntity>>
    fun getComment(accountId: String, postId: String, commentId: String): Observable<Optional<CommentEntity>>
    fun addComment(comment: CommentEntity): Completable

    fun getBody(accountId: String, bodyId: String): Observable<Optional<BodyEntity>>
    fun addBody(body: BodyEntity): Completable
}