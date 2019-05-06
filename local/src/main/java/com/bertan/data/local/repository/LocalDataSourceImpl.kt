package com.bertan.data.local.repository

import com.bertan.data.local.db.LocalDatabase
import com.bertan.data.local.mapper.AccountEntityMapper.asAccountModel
import com.bertan.data.local.mapper.AccountModelMapper.asAccountEntity
import com.bertan.data.local.mapper.CommentEntityMapper.asCommentModel
import com.bertan.data.local.mapper.CommentModelMapper.asCommentEntity
import com.bertan.data.local.mapper.PostEntityMapper.asPostModel
import com.bertan.data.local.mapper.PostModelMapper.asPostEntity
import com.bertan.data.model.AccountEntity
import com.bertan.data.model.CommentEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.model.SourceEntity
import com.bertan.data.repository.LocalDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

class LocalDataSourceImpl(private val database: LocalDatabase) : LocalDataSource {
    override fun getSources(): Observable<List<SourceEntity>> =
        Observable.just(
            listOf(
                SourceEntity(
                    "1",
                    "JSONPlaceholder",
                    "https://avatars1.githubusercontent.com/u/5502029",
                    SourceEntity.StateEntity.Enabled,
                    SourceEntity.ColourEntity.RGB(76, 175, 80)
                ),
                SourceEntity(
                    UUID.randomUUID().toString(),
                    "MyUnicornSocial",
                    "https://api.adorable.io/avatars/200/MyUnicornSocial",
                    SourceEntity.StateEntity.Disabled,
                    SourceEntity.ColourEntity.RGB(202, 239, 148)
                ),
                SourceEntity(
                    UUID.randomUUID().toString(),
                    "YouSocialLife",
                    "https://api.adorable.io/avatars/200/YouSocialLife",
                    SourceEntity.StateEntity.Disabled,
                    SourceEntity.ColourEntity.RGB(209, 125, 123)
                )
            )
        )

    override fun getAccounts(): Observable<List<AccountEntity>> =
        database.accountDao.accounts()
            .map { result -> result.map { it.asAccountEntity } }

    override fun getAccount(accountId: String): Observable<Optional<AccountEntity>> =
        database.accountDao.account(accountId)
            .map { result -> Optional.of(result.asAccountEntity) }
            .onErrorReturnItem(Optional.empty())
            .toObservable()

    override fun addAccount(account: AccountEntity): Completable =
        Completable.defer {
            database.accountDao.insert(account.asAccountModel)
            Completable.complete()
        }

    override fun getPosts(): Observable<List<PostEntity>> =
        database.postDao.posts()
            .map { result -> result.map { it.asPostEntity } }
            .toObservable()

    override fun getPost(postId: String): Observable<Optional<PostEntity>> =
        database.postDao.post(postId)
            .map { result -> Optional.of(result.asPostEntity) }
            .onErrorReturnItem(Optional.empty())
            .toObservable()

    override fun addPost(post: PostEntity): Completable =
        Completable.defer {
            database.postDao.insert(post.asPostModel)
            Completable.complete()
        }

    override fun getCommentsByPost(postId: String): Observable<List<CommentEntity>> =
        database.commentDao.commentsByPost(postId)
            .map { result -> result.map { it.asCommentEntity } }
            .toObservable()

    override fun getComment(postId: String, commentId: String): Observable<Optional<CommentEntity>> =
        database.commentDao.comment(postId, commentId)
            .map { result -> Optional.of(result.asCommentEntity) }
            .onErrorReturnItem(Optional.empty())
            .toObservable()

    override fun addComment(comment: CommentEntity): Completable =
        Completable.defer {
            database.commentDao.insert(comment.asCommentModel)
            Completable.complete()
        }
}