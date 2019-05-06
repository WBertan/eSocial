package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.comment.AddComment
import com.bertan.domain.interactor.comment.GetComment
import com.bertan.domain.model.Comment
import com.bertan.presentation.mapper.CommentMapper.asCommentView
import com.bertan.presentation.state.ViewState
import com.bertan.presentation.test.CommentDataFactory
import com.bertan.presentation.test.CommentViewDataFactory
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class CommentViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var commentViewModel: CommentViewModel

    @MockK(relaxed = true)
    private lateinit var getCommentUseCase: GetComment
    @MockK(relaxed = true)
    private lateinit var addCommentUseCase: AddComment
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        commentViewModel = CommentViewModel(getCommentUseCase, addCommentUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(commentViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should not interact with the use cases`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        confirmVerified(getCommentUseCase)
        confirmVerified(addCommentUseCase)
    }

    @Test
    fun `given an commentId when call getComment it should post loading state`() {
        commentViewModel.getComment("postId", "commentId")

        assertEquals(ViewState.Loading, commentViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes getComment it should post success state`() {
        val comment = CommentDataFactory.get()
        every { getCommentUseCase.execute(any(), captureLambda(), any(), any()) } answers {
            lambda<Function1<Optional<Comment>, Unit>>().invoke(Optional.of(comment))
            mockk()
        }

        commentViewModel.getComment("postId", "commentId")

        assertEquals(ViewState.Success(Optional.of(comment.asCommentView)), commentViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes getComment it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getCommentUseCase.execute(any(), any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        commentViewModel.getComment("postId", "commentId")

        assertEquals(
            ViewState.Error("Failed to load Comment(postId, commentId)!", dummyError),
            commentViewModel.getState().value
        )
    }

    @Test
    fun `given an comment when call addComment it should post loading state`() {
        val commentView = CommentViewDataFactory.get()
        commentViewModel.addComment(commentView)

        assertEquals(ViewState.Loading, commentViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes addComment it should post success state`() {
        val commentView = CommentViewDataFactory.get()
        every { addCommentUseCase.execute(any(), captureLambda(), any()) } answers {
            lambda<Function0<Unit>>().invoke()
            mockk()
        }

        commentViewModel.addComment(commentView)

        assertEquals(ViewState.Success(Optional.of(commentView)), commentViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes addComment it should post error state`() {
        val commentView = CommentViewDataFactory.get()
        val dummyError = Exception("dummyError")
        every { addCommentUseCase.execute(any(), any(), captureLambda()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        commentViewModel.addComment(commentView)

        assertEquals(
            ViewState.Error("Failed to add $commentView!", dummyError),
            commentViewModel.getState().value
        )
    }
}