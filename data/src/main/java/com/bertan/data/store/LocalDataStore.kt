package com.bertan.data.store

import com.bertan.data.model.AccountEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.model.SourceEntity
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

    override fun getPost(postId: String): Observable<Optional<PostEntity>> =
        localDataSource.getPost(postId)

    override fun addPost(post: PostEntity): Completable =
        localDataSource.addPost(post)

    override fun getCommentsByPost(postId: String): Observable<List<CommentEntity>> =
        localDataSource.getCommentsByPost(postId)

    override fun getComment(postId: String, commentId: String): Observable<Optional<CommentEntity>> =
        localDataSource.getComment(postId, commentId)

    override fun addComment(comment: CommentEntity): Completable =
        localDataSource.addComment(comment)
}