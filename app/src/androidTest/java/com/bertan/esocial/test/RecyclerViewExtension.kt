package com.bertan.esocial.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher

internal fun atPositionOnView(position: Int, itemMatcher: Matcher<View>, targetViewId: Int): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position) ?: return false // has no item on such position
            return itemMatcher.matches(viewHolder.itemView.findViewById(targetViewId))
        }
    }

internal class itemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) throw noViewFoundException
        val adapter = (view as? RecyclerView)?.adapter
        assertThat(adapter?.itemCount, `is`(expectedCount))
    }
}