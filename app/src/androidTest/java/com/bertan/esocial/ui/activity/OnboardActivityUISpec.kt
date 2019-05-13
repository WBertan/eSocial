package com.bertan.esocial.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.bertan.esocial.R
import com.bertan.esocial.extension.showMessage
import com.bertan.esocial.test.SourceViewDataUIFactory
import com.bertan.esocial.test.atPositionOnView
import com.bertan.esocial.test.itemCountAssertion
import com.bertan.presentation.model.SourceView
import com.bertan.presentation.state.ViewState
import com.bertan.presentation.vm.SourceListViewModel
import io.mockk.*
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

class OnboardActivityUISpec {
    private val liveData: MutableLiveData<ViewState<List<SourceView>>> = MutableLiveData()
    private val sourceListViewModel: SourceListViewModel = spyk(SourceListViewModel(mockk(relaxed = true))) {
        every { getState() } returns liveData
        every { onCreate() } returns Unit
    }

    private val testModule: Module = module(override = true) {
        viewModel { sourceListViewModel }
    }

    @get:Rule
    val activityRule: ActivityTestRule<OnboardActivity> = ActivityTestRule(OnboardActivity::class.java, true, false)

    @Before
    fun setup() {
        loadKoinModules(listOf(testModule))
    }

    private fun launchActivity() = activityRule.launchActivity(null)

    @Test
    fun given_activity_when_created_it_should_display_views() {
        launchActivity()

        onView(withId(R.id.logo))
            .check(matches(isDisplayed()))
        onView(withId(R.id.appName))
            .check(matches(isDisplayed()))
        onView(withId(R.id.description))
            .check(matches(isDisplayed()))
        onView(withId(R.id.sources))
            .check(matches(allOf(not(isDisplayed()), withEffectiveVisibility(Visibility.VISIBLE))))
    }

    @Test
    fun given_error_when_loading_sources_it_should_display_an_error_message_and_not_set_the_adapter_in_the_list() {
        launchActivity()

        mockkStatic("com.bertan.esocial.extension.ActivityExtensionKt")

        every { activityRule.activity.showMessage(any()) } returns Unit

        liveData.postValue(ViewState.Error("dummyMessage", Exception("dummyException")))

        onView(withId(R.id.sources))
            .check(matches(allOf(not(isDisplayed()), withEffectiveVisibility(Visibility.VISIBLE))))
        verify(exactly = 1) { activityRule.activity.showMessage("Error: dummyMessage") }

        unmockkStatic("com.bertan.esocial.extension.ActivityExtensionKt")
    }

    @Test
    fun given_success_when_loading_sources_it_should_load_the_list_adapter_with_the_results() {
        launchActivity()

        val sources =
            listOf(
                SourceViewDataUIFactory.get().copy(state = SourceView.StateView.Enabled),
                SourceViewDataUIFactory.get().copy(state = SourceView.StateView.Disabled)
            )

        liveData.postValue(ViewState.Success(sources))


        onView(withId(R.id.sources))
            .check(matches(isDisplayed()))
            .check(itemCountAssertion(2))
            .check(matches(atPositionOnView(0, allOf(withText(sources.first().name), isEnabled()), R.id.button)))
            .check(matches(atPositionOnView(1, allOf(withText(sources.last().name), not(isEnabled())), R.id.button)))
    }
}