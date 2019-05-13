package com.bertan.esocial.ui.activity

import android.view.View
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bertan.esocial.R
import com.bertan.esocial.TestApplication
import com.bertan.esocial.extension.showMessage
import com.bertan.esocial.test.SourceViewDataFactory
import com.bertan.presentation.model.SourceView
import com.bertan.presentation.state.ViewState
import com.bertan.presentation.vm.SourceListViewModel
import com.squareup.picasso.Picasso
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

typealias SourceViewModelState = List<SourceView>
typealias SourceViewModelObserver = Observer<ViewState<SourceViewModelState>>

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class OnboardActivitySpec : KoinTest {
    private var sourceViewModelStateObserver: SourceViewModelObserver? = null

    private val sourceListViewModel: SourceListViewModel = mockk {
        every { getState() } answers {
            mockk {
                val slot = CapturingSlot<SourceViewModelObserver>()
                every { observe(any(), capture(slot)) } answers {
                    sourceViewModelStateObserver = slot.captured
                }
            }
        }
        every { onCreate() } returns Unit
    }

    private val testModule: Module = module {
        viewModel { sourceListViewModel }
    }

    private lateinit var activityController: ActivityController<OnboardActivity>

    @Before
    fun setup() {
        startKoin(listOf(testModule))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun createActivity(): ActivityController<OnboardActivity> =
        buildActivity(OnboardActivity::class.java)
            .also { activityController = it }

    @Test
    fun `given activity when created it should display views`() {
        createActivity().setup()

        val logo = activityController.get().findViewById<View>(R.id.logo)
        val appName = activityController.get().findViewById<View>(R.id.appName)
        val description = activityController.get().findViewById<View>(R.id.description)
        val sources = activityController.get().findViewById<View>(R.id.sources)

        assertEquals(VISIBLE, logo.visibility)
        assertEquals(VISIBLE, appName.visibility)
        assertEquals(VISIBLE, description.visibility)
        assertEquals(VISIBLE, sources.visibility)
    }

    @Test
    fun `given error when loading sources it should display an error message and not set the adapter in the list`() {
        createActivity().setup()

        mockkStatic("com.bertan.esocial.extension.ActivityExtensionKt")

        every { activityController.get().showMessage(any()) } returns Unit

        sourceViewModelStateObserver?.onChanged(ViewState.Error("dummyMessage", Exception("dummyException")))

        val sources = activityController.get().findViewById<RecyclerView>(R.id.sources)

        assertNull(sources.adapter)
        verify(exactly = 1) { activityController.get().showMessage("Error: dummyMessage") }

        unmockkStatic("com.bertan.esocial.extension.ActivityExtensionKt")
    }

    @Test
    fun `given success when loading sources it should load the list adapter with the results`() {
        Picasso.setSingletonInstance(mockk(relaxed = true))

        createActivity().setup()

        sourceViewModelStateObserver?.onChanged(ViewState.Success(SourceViewDataFactory.get(2)))

        val sources = activityController.get().findViewById<RecyclerView>(R.id.sources)

        val adapter = sources.adapter

        assertNotNull(adapter)
        assertEquals(2, sources.childCount)
    }
}