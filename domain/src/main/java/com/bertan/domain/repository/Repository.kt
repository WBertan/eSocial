package com.bertan.domain.repository

import com.bertan.domain.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

interface Repository :
    SourcesRepository,
    AccountsRepository,
    PostsRepository,
    CommentsRepository,
    BodiesRepository

interface SourcesRepository {
    fun getSources(): Observable<List<Source>>
}

interface AccountsRepository {
    fun getAccounts(): Observable<List<Account>>
    fun getAccount(accountId: String): Observable<Optional<Account>>
    fun addAccount(account: Account): Completable
}

interface PostsRepository {
    fun getPosts(): Observable<List<Post>>
    fun getPost(postId: String): Observable<Optional<Post>>
    fun addPost(post: Post): Completable
}

interface CommentsRepository {
    fun getCommentsByPost(postId: String): Observable<List<Comment>>
    fun getComment(postId: String, commentId: String): Observable<Optional<Comment>>
    fun addComment(comment: Comment): Completable
}

interface BodiesRepository {
    fun getBody(bodyId: String): Observable<Optional<Body>>
    fun addBody(body: Body): Completable
}