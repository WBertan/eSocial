package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.post.GetPosts
import com.bertan.domain.model.Post
import com.bertan.presentation.state.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostListViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postListViewModel: PostListViewModel

    @MockK(relaxed = true)
    private lateinit var getPostsUseCase: GetPosts
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        postListViewModel = PostListViewModel(getPostsUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(postListViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should execute use case`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        verify(exactly = 1) { getPostsUseCase.execute(null, any(), any(), any()) }
        confirmVerified(getPostsUseCase)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should post loading state`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Loading, postListViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes it should post success state`() {
        every { getPostsUseCase.execute(null, captureLambda(), any(), any()) } answers {
            lambda<Function1<List<Post>, Unit>>().invoke(emptyList())
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Success(emptyList<Post>()), postListViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getPostsUseCase.execute(null, any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Error("Failed to load Posts!", dummyError), postListViewModel.getState().value)
    }
}