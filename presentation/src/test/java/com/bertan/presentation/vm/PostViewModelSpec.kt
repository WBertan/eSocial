package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.post.AddPost
import com.bertan.domain.interactor.post.GetPost
import com.bertan.domain.model.Post
import com.bertan.presentation.mapper.PostMapper.asPostView
import com.bertan.presentation.state.ViewState
import com.bertan.presentation.test.PostDataFactory
import com.bertan.presentation.test.PostViewDataFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class PostViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postViewModel: PostViewModel

    @MockK(relaxed = true)
    private lateinit var getPostUseCase: GetPost
    @MockK(relaxed = true)
    private lateinit var addPostUseCase: AddPost
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        postViewModel = PostViewModel(getPostUseCase, addPostUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(postViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should not interact with the use cases`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        confirmVerified(getPostUseCase)
        confirmVerified(addPostUseCase)
    }

    @Test
    fun `given an postId when call getPost it should post loading state`() {
        postViewModel.getPost("postId")

        assertEquals(ViewState.Loading, postViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes getPost it should post success state`() {
        val post = PostDataFactory.get()
        every { getPostUseCase.execute(any(), captureLambda(), any(), any()) } answers {
            lambda<Function1<Optional<Post>, Unit>>().invoke(Optional.of(post))
            mockk()
        }

        postViewModel.getPost("postId")

        assertEquals(ViewState.Success(Optional.of(post.asPostView)), postViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes getPost it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getPostUseCase.execute(any(), any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        postViewModel.getPost("postId")

        assertEquals(
            ViewState.Error("Failed to load Post(postId)!", dummyError),
            postViewModel.getState().value
        )
    }

    @Test
    fun `given an post when call addPost it should post loading state`() {
        val postView = PostViewDataFactory.get()
        postViewModel.addPost(postView)

        assertEquals(ViewState.Loading, postViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes addPost it should post success state`() {
        val postView = PostViewDataFactory.get()
        every { addPostUseCase.execute(any(), captureLambda(), any()) } answers {
            lambda<Function0<Unit>>().invoke()
            mockk()
        }

        postViewModel.addPost(postView)

        assertEquals(ViewState.Success(Optional.of(postView)), postViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes addPost it should post error state`() {
        val postView = PostViewDataFactory.get()
        val dummyError = Exception("dummyError")
        every { addPostUseCase.execute(any(), any(), captureLambda()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        postViewModel.addPost(postView)

        assertEquals(
            ViewState.Error("Failed to add $postView!", dummyError),
            postViewModel.getState().value
        )
    }
}