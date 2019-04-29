package com.bertan.data.store

import com.bertan.data.model.*
import com.bertan.data.repository.LocalDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

class LocalDataStore(private val localDataSource: LocalDataSource) : DataStore {
    override fun getSources(): Observable<List<SourceEntity>> =
        localDataSource.getSources()

    override fun getAccounts(): Observable<List<AccountEntity>> =
        localDataSource.getAccounts()

    override fun getAccount(accountId: String): Observable<Optional<AccountEntity>> =
        localDataSource.getAccount(accountId)

    override fun addAccount(account: AccountEntity): Completable =
        localDataSource.addAccount(account)

    override fun getPosts(): Observable<List<PostEntity>> =
        localDataSource.getPosts()

    override fun getPostsByAccount(accountId: String): Observable<List<PostEntity>> =
        localDataSource.getPostsByAccount(accountId)

    override fun getPost(accountId: String, postId: String): Observable<Optional<PostEntity>> =
        localDataSource.getPost(accountId, postId)

    override fun addPost(post: PostEntity): Completable =
        localDataSource.addPost(post)

    override fun getCommentsByPost(accountId: String, postId: String): Observable<List<CommentEntity>> =
        localDataSource.getCommentsByPost(accountId, postId)

    override fun getComment(accountId: String, postId: String, commentId: String): Observable<Optional<CommentEntity>> =
        localDataSource.getComment(accountId, postId, commentId)

    override fun addComment(comment: CommentEntity): Completable =
        localDataSource.addComment(comment)

    override fun getBody(accountId: String, bodyId: String): Observable<Optional<BodyEntity>> =
        localDataSource.getBody(accountId, bodyId)

    override fun addBody(body: BodyEntity): Completable =
        localDataSource.addBody(body)
}