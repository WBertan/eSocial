package com.bertan.data.remote.repository

import com.bertan.data.model.CommentEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.remote.mapper.CommentMapper.asCommentEntity
import com.bertan.data.remote.mapper.PostMapper.asPostEntity
import com.bertan.data.remote.service.JSONPlaceholderService
import com.bertan.data.repository.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.*

class RemoteDataSourceImpl(private val service: JSONPlaceholderService) : RemoteDataSource {
    override fun getPosts(): Observable<List<PostEntity>> =
        service.getPosts()
            .map { result -> result.map { it.asPostEntity } }

    override fun getPost(postId: String): Observable<Optional<PostEntity>> =
        validateArgument("postId", postId)
            .flatMap(service::getPost)
            .map { result -> Optional.of(result.asPostEntity) }

    override fun getCommentsByPost(postId: String): Observable<List<CommentEntity>> =
        validateArgument("postId", postId)
            .flatMap(service::getCommentsByPost)
            .map { result -> result.map { it.asCommentEntity } }

    override fun getComment(postId: String, commentId: String): Observable<Optional<CommentEntity>> =
        Observable.zip(
            validateArgument("postId", postId),
            validateArgument("commentId", commentId),
            BiFunction<Long, Long, Long> { _, commentIdValidated -> commentIdValidated }
        )
            .flatMap(service::getComment)
            .map { result -> Optional.of(result.asCommentEntity) }

    private fun validateArgument(argumentName: String, argument: String): Observable<Long> =
        argument.toLongOrNull()
            ?.let { Observable.just(it) }
            ?: Observable.error(IllegalArgumentException("Invalid $argumentName."))
}