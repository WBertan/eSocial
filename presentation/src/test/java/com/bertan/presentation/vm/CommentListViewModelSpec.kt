package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.comment.GetCommentsByPost
import com.bertan.domain.model.Comment
import com.bertan.presentation.state.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentListViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var commentListViewModel: CommentListViewModel

    @MockK(relaxed = true)
    private lateinit var getCommentsByPostUseCase: GetCommentsByPost
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        commentListViewModel = CommentListViewModel("postId", getCommentsByPostUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(commentListViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should execute use case`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        verify(exactly = 1) { getCommentsByPostUseCase.execute(any(), any(), any(), any()) }
        confirmVerified(getCommentsByPostUseCase)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should post loading state`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Loading, commentListViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes it should post success state`() {
        every { getCommentsByPostUseCase.execute(any(), captureLambda(), any(), any()) } answers {
            lambda<Function1<List<Comment>, Unit>>().invoke(emptyList())
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Success(emptyList<Comment>()), commentListViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getCommentsByPostUseCase.execute(any(), any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(
            ViewState.Error("Failed to load Comments for Post(postId)!", dummyError),
            commentListViewModel.getState().value
        )
    }
}