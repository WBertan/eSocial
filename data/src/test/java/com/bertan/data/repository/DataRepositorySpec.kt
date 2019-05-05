package com.bertan.data.repository

import com.bertan.data.mapper.AccountMapper.asAccountEntity
import com.bertan.data.mapper.BodyMapper.asBodyEntity
import com.bertan.data.mapper.CommentMapper.asCommentEntity
import com.bertan.data.mapper.PostMapper.asPostEntity
import com.bertan.data.mapper.SourceMapper.asSourceEntity
import com.bertan.data.model.*
import com.bertan.data.store.DataStore
import com.bertan.data.test.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

class DataRepositorySpec {
    private class TestDataStore : DataStore {
        private val <T> T?.asOptional: Optional<T>
            get() = Optional.ofNullable(this)

        private val sources: MutableList<SourceEntity> = mutableListOf()
        private val accounts: MutableList<AccountEntity> = mutableListOf()
        private val posts: MutableList<PostEntity> = mutableListOf()
        private val comments: MutableList<CommentEntity> = mutableListOf()
        private val bodies: MutableList<BodyEntity> = mutableListOf()

        override fun getSources(): Observable<List<SourceEntity>> =
            Observable.just(sources)

        override fun getAccounts(): Observable<List<AccountEntity>> =
            Observable.just(accounts)

        override fun getAccount(accountId: String): Observable<Optional<AccountEntity>> =
            Observable.just(accounts.find { it.id == accountId }.asOptional)

        override fun addAccount(account: AccountEntity): Completable =
            Completable.fromAction { accounts.add(account) }

        override fun getPosts(): Observable<List<PostEntity>> =
            Observable.just(posts)

        override fun getPost(postId: String): Observable<Optional<PostEntity>> =
            Observable.just(posts.find { it.id == postId }.asOptional)

        override fun addPost(post: PostEntity): Completable =
            Completable.fromAction { posts.add(post) }

        override fun getCommentsByPost(postId: String): Observable<List<CommentEntity>> =
            Observable.just(comments.filter { it.postId == postId })

        override fun getComment(postId: String, commentId: String): Observable<Optional<CommentEntity>> =
            Observable.just(comments.find { it.postId == postId && it.id == commentId }.asOptional)

        override fun addComment(comment: CommentEntity): Completable =
            Completable.fromAction { comments.add(comment) }

        override fun getBody(bodyId: String): Observable<Optional<BodyEntity>> =
            Observable.just(bodies.find { it.id == bodyId }.asOptional)

        override fun addBody(body: BodyEntity): Completable =
            Completable.fromAction { bodies.add(body) }
    }

    lateinit var dataRepository: DataRepository

    lateinit var localDataStore: DataStore

    @MockK
    lateinit var remoteDataStore: DataStore

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        localDataStore = spyk(TestDataStore())
        dataRepository = DataRepository(localDataStore, remoteDataStore)
    }

    private fun <T> TestObserver<T>.assertCompletedValue(expected: T) {
        assertComplete()
        assertValue(expected)
    }

    @Test
    fun `given a response when getSources it should completes and return data`() {
        val sources = SourceDataFactory.get(2)
        val sourcesEntity = sources.map { it.asSourceEntity }
        every { localDataStore.getSources() } returns Observable.just(sourcesEntity)

        val result = dataRepository.getSources().test()

        result.assertCompletedValue(sources)
    }

    @Test
    fun `given a response when getAccounts it should completes and return data`() {
        val accounts = AccountDataFactory.get(2)
        val accountsEntity = accounts.map { it.asAccountEntity }
        every { localDataStore.getAccounts() } returns Observable.just(accountsEntity)

        val result = dataRepository.getAccounts().test()

        result.assertCompletedValue(accounts)
    }

    @Test
    fun `given a found response when getAccount it should completes and return data`() {
        val account = AccountDataFactory.get()
        val accountEntity = account.asAccountEntity
        every { localDataStore.getAccount(any()) } returns Observable.just(Optional.of(accountEntity))

        val result = dataRepository.getAccount("accountId").test()

        result.assertCompletedValue(Optional.of(account))
    }

    @Test
    fun `given a not found response when getAccount it should completes and return data`() {
        every { localDataStore.getAccount(any()) } returns Observable.just(Optional.empty())

        val result = dataRepository.getAccount("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addAccount it should completes`() {
        every { localDataStore.addAccount(any()) } returns Completable.complete()

        val result = dataRepository.addAccount(AccountDataFactory.get()).test()

        result.assertComplete()
    }

    @Test
    fun `given a response when getPosts it should completes and return data`() {
        val localPosts = PostDataFactory.get(2)
        val localPostsEntity = localPosts.map { it.asPostEntity }

        localDataStore.addPost(localPosts.first().asPostEntity).test().assertComplete()
        localDataStore.addPost(localPosts.last().asPostEntity).test().assertComplete()
        localDataStore.getPosts().test().assertCompletedValue(localPostsEntity)

        val remotePosts = PostDataFactory.get(2)
        val remotePostsEntity = remotePosts.map { it.asPostEntity }
        every { remoteDataStore.getPosts() } returns Observable.just(remotePostsEntity)

        val result = dataRepository.getPosts().test()

        result.assertCompletedValue(localPosts + remotePosts)
    }

    @Test
    fun `given remote failure when getPosts it should completes and return local data`() {
        val localPosts = PostDataFactory.get(2)
        val localPostsEntity = localPosts.map { it.asPostEntity }

        localDataStore.addPost(localPosts.first().asPostEntity).test().assertComplete()
        localDataStore.addPost(localPosts.last().asPostEntity).test().assertComplete()
        localDataStore.getPosts().test().assertCompletedValue(localPostsEntity)

        every { remoteDataStore.getPosts() } returns Observable.error(Exception("error"))

        val result = dataRepository.getPosts().test()

        result.assertCompletedValue(localPosts)
    }

    @Test
    fun `given remote response when getPosts it should completes and populate local with remote data and return data`() {
        val remotePosts = PostDataFactory.get(2)
        val remotePostsEntity = remotePosts.map { it.asPostEntity }
        every { remoteDataStore.getPosts() } returns Observable.just(remotePostsEntity)

        val result = dataRepository.getPosts().test()

        localDataStore.getPosts().test().assertCompletedValue(remotePostsEntity)
        result.assertCompletedValue(remotePosts)
    }

    @Test
    fun `given a response when getPost it should completes and return data`() {
        val localPost = PostDataFactory.get().copy(id = "postId")
        val localPostEntity = localPost.asPostEntity

        localDataStore.addPost(localPost.asPostEntity).test().assertComplete()
        localDataStore.getPost("postId").test().assertCompletedValue(Optional.of(localPostEntity))

        val remotePost = PostDataFactory.get().copy(id = "postId")
        val remotePostEntity = remotePost.asPostEntity
        every { remoteDataStore.getPost(any()) } returns Observable.just(Optional.of(remotePostEntity))

        val result = dataRepository.getPost("postId").test()

        result.assertCompletedValue(Optional.of(localPost))
    }

    @Test
    fun `given remote failure when getPost it should completes and return local data`() {
        val localPost = PostDataFactory.get().copy(id = "postId")
        val localPostEntity = localPost.asPostEntity

        localDataStore.addPost(localPost.asPostEntity).test().assertComplete()
        localDataStore.getPost("postId").test().assertCompletedValue(Optional.of(localPostEntity))

        every { remoteDataStore.getPost(any()) } returns Observable.error(Exception("error"))

        val result = dataRepository.getPost("postId").test()

        result.assertCompletedValue(Optional.of(localPost))
    }

    @Test
    fun `given remote not found when getPost it should completes and return local data`() {
        val localPost = PostDataFactory.get().copy(id = "postId")
        val localPostEntity = localPost.asPostEntity

        localDataStore.addPost(localPost.asPostEntity).test().assertComplete()
        localDataStore.getPost("postId").test().assertCompletedValue(Optional.of(localPostEntity))

        every { remoteDataStore.getPost(any()) } returns Observable.just(Optional.empty())

        val result = dataRepository.getPost("postId").test()

        result.assertCompletedValue(Optional.of(localPost))
    }

    @Test
    fun `given remote response when getPost it should completes and populate local with remote data and return data`() {
        val remotePost = PostDataFactory.get().copy(id = "postId")
        val remotePostEntity = remotePost.asPostEntity
        every { remoteDataStore.getPost(any()) } returns Observable.just(Optional.of(remotePostEntity))

        val result = dataRepository.getPost("postId").test()

        localDataStore.getPost("postId").test().assertCompletedValue(Optional.of(remotePostEntity))
        result.assertCompletedValue(Optional.of(remotePost))
    }

    @Test
    fun `given a response when addPost it should completes`() {
        every { localDataStore.addPost(any()) } returns Completable.complete()

        val result = dataRepository.addPost(PostDataFactory.get()).test()

        result.assertComplete()
    }

    @Test
    fun `given a response when getCommentsByPost it should completes and return data`() {
        val localComments = CommentDataFactory.get(2).map { it.copy(postId = "postId") }
        val localCommentsEntity = localComments.map { it.asCommentEntity }

        localDataStore.addComment(localComments.first().asCommentEntity).test().assertComplete()
        localDataStore.addComment(localComments.last().asCommentEntity).test().assertComplete()
        localDataStore.getCommentsByPost("postId").test().assertCompletedValue(localCommentsEntity)

        val remoteComments = CommentDataFactory.get(2).map { it.copy(postId = "postId") }
        val remoteCommentsEntity = remoteComments.map { it.asCommentEntity }
        every { remoteDataStore.getCommentsByPost(any()) } returns Observable.just(remoteCommentsEntity)

        val result = dataRepository.getCommentsByPost("postId").test()

        result.assertCompletedValue(localComments + remoteComments)
    }

    @Test
    fun `given remote failure when getCommentsByPost it should completes and return local data`() {
        val localComments = CommentDataFactory.get(2).map { it.copy(postId = "postId") }
        val localCommentsEntity = localComments.map { it.asCommentEntity }

        localDataStore.addComment(localComments.first().asCommentEntity).test().assertComplete()
        localDataStore.addComment(localComments.last().asCommentEntity).test().assertComplete()
        localDataStore.getCommentsByPost("postId").test().assertCompletedValue(localCommentsEntity)

        every { remoteDataStore.getCommentsByPost(any()) } returns Observable.error(Exception("error"))

        val result = dataRepository.getCommentsByPost("postId").test()

        result.assertCompletedValue(localComments)
    }

    @Test
    fun `given remote response when getCommentsByPost it should completes and populate local with remote data and return data`() {
        val remoteComments = CommentDataFactory.get(2).map { it.copy(postId = "postId") }
        val remoteCommentsEntity = remoteComments.map { it.asCommentEntity }
        every { remoteDataStore.getCommentsByPost(any()) } returns Observable.just(remoteCommentsEntity)

        val result = dataRepository.getCommentsByPost("postId").test()

        localDataStore.getCommentsByPost("postId").test().assertCompletedValue(remoteCommentsEntity)
        result.assertCompletedValue(remoteComments)
    }

    @Test
    fun `given a response when getComment it should completes and return data`() {
        val localComment = CommentDataFactory.get().copy(postId = "postId", id = "commentId")
        val localCommentEntity = localComment.asCommentEntity

        localDataStore.addComment(localComment.asCommentEntity).test().assertComplete()
        localDataStore.getComment("postId", "commentId").test()
            .assertCompletedValue(Optional.of(localCommentEntity))

        val remoteComment = CommentDataFactory.get().copy(postId = "postId", id = "commentId")
        val remoteCommentEntity = remoteComment.asCommentEntity
        every { remoteDataStore.getComment(any(), any()) } returns Observable.just(
            Optional.of(
                remoteCommentEntity
            )
        )

        val result = dataRepository.getComment("postId", "commentId").test()

        result.assertCompletedValue(Optional.of(localComment))
    }

    @Test
    fun `given remote failure when getComment it should completes and return local data`() {
        val localComment = CommentDataFactory.get().copy(postId = "postId", id = "commentId")
        val localCommentEntity = localComment.asCommentEntity

        localDataStore.addComment(localComment.asCommentEntity).test().assertComplete()
        localDataStore.getComment("postId", "commentId").test()
            .assertCompletedValue(Optional.of(localCommentEntity))

        every { remoteDataStore.getComment(any(), any()) } returns Observable.error(Exception("error"))

        val result = dataRepository.getComment("postId", "commentId").test()

        result.assertCompletedValue(Optional.of(localComment))
    }

    @Test
    fun `given remote not found when getComment it should completes and return local data`() {
        val localComment = CommentDataFactory.get().copy(postId = "postId", id = "commentId")
        val localCommentEntity = localComment.asCommentEntity

        localDataStore.addComment(localComment.asCommentEntity).test().assertComplete()
        localDataStore.getComment("postId", "commentId").test()
            .assertCompletedValue(Optional.of(localCommentEntity))

        every { remoteDataStore.getComment(any(), any()) } returns Observable.just(Optional.empty())

        val result = dataRepository.getComment("postId", "commentId").test()

        result.assertCompletedValue(Optional.of(localComment))
    }

    @Test
    fun `given remote response when getComment it should completes and populate local with remote data and return data`() {
        val remoteComment = CommentDataFactory.get().copy(postId = "postId", id = "commentId")
        val remoteCommentEntity = remoteComment.asCommentEntity
        every { remoteDataStore.getComment(any(), any()) } returns Observable.just(
            Optional.of(
                remoteCommentEntity
            )
        )

        val result = dataRepository.getComment("postId", "commentId").test()

        localDataStore.getComment("postId", "commentId").test()
            .assertCompletedValue(Optional.of(remoteCommentEntity))
        result.assertCompletedValue(Optional.of(remoteComment))
    }

    @Test
    fun `given a response when addComment it should completes`() {
        every { localDataStore.addComment(any()) } returns Completable.complete()

        val result = dataRepository.addComment(CommentDataFactory.get()).test()

        result.assertComplete()
    }

    @Test
    fun `given a found response when getBody it should completes and return data`() {
        val body = BodyDataFactory.get()
        val bodyEntity = body.asBodyEntity
        every { localDataStore.getBody(any()) } returns Observable.just(Optional.of(bodyEntity))

        val result = dataRepository.getBody("bodyId").test()

        result.assertCompletedValue(Optional.of(body))
    }

    @Test
    fun `given a not found response when getBody it should completes and return data`() {
        every { localDataStore.getBody(any()) } returns Observable.just(Optional.empty())

        val result = dataRepository.getBody("notFoundId").test()

        result.assertCompletedValue(Optional.empty())
    }

    @Test
    fun `given a response when addBody it should completes`() {
        every { localDataStore.addBody(any()) } returns Completable.complete()

        val result = dataRepository.addBody(BodyDataFactory.get()).test()

        result.assertComplete()
    }
}