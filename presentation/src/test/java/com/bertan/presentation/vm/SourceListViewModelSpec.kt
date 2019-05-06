package com.bertan.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bertan.domain.interactor.source.GetSources
import com.bertan.domain.model.Source
import com.bertan.presentation.state.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SourceListViewModelSpec {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sourceListViewModel: SourceListViewModel

    @MockK(relaxed = true)
    private lateinit var getSourcesUseCase: GetSources
    @MockK
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sourceListViewModel = SourceListViewModel(getSourcesUseCase)

        lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.addObserver(sourceListViewModel)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should execute use case`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        verify(exactly = 1) { getSourcesUseCase.execute(null, any(), any(), any()) }
        confirmVerified(getSourcesUseCase)
    }

    @Test
    fun `given a lifecycle when ON_CREATE it should post loading state`() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Loading, sourceListViewModel.getState().value)
    }

    @Test
    fun `given a success use case when executes it should post success state`() {
        every { getSourcesUseCase.execute(null, captureLambda(), any(), any()) } answers {
            lambda<Function1<List<Source>, Unit>>().invoke(emptyList())
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Success(emptyList<Source>()), sourceListViewModel.getState().value)
    }

    @Test
    fun `given a failure use case when executes it should post error state`() {
        val dummyError = Exception("dummyError")
        every { getSourcesUseCase.execute(null, any(), captureLambda(), any()) } answers {
            lambda<Function1<Throwable, Unit>>().invoke(dummyError)
            mockk()
        }

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        assertEquals(ViewState.Error("Failed to load Sources!", dummyError), sourceListViewModel.getState().value)
    }
}