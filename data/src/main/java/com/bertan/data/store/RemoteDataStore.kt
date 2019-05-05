package com.bertan.data.store

import com.bertan.data.model.AccountEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.model.SourceEntity
import com.bertan.data.repository.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

class RemoteDataStore(private val remoteDataSource: RemoteDataSource) : DataStore {
    private val unsupportedOperation = UnsupportedOperationException("Operation not supported by RemoteDataStore!")

    override fun getSources(): Observable<List<SourceEntity>> =
        throw unsupportedOperation

    override fun getAccounts(): Observable<List<AccountEntity>> =
        throw unsupportedOperation

    override fun getAccount(accountId: String): Observable<Optional<AccountEntity>> =
        throw unsupportedOperation

    override fun addAccount(account: AccountEntity): Completable =
        throw unsupportedOperation

    override fun getPosts(): Observable<List<PostEntity>> =
        remoteDataSource.getPosts()

    override fun getPost(postId: String): Observable<Optional<PostEntity>> =
        remoteDataSource.getPost(postId)

    override fun addPost(post: PostEntity): Completable =
        throw unsupportedOperation

    override fun getCommentsByPost(postId: String): Observable<List<CommentEntity>> =
        remoteDataSource.getCommentsByPost(postId)

    override fun getComment(postId: String, commentId: String): Observable<Optional<CommentEntity>> =
        remoteDataSource.getComment(postId, commentId)

    override fun addComment(comment: CommentEntity): Completable =
        throw unsupportedOperation
}