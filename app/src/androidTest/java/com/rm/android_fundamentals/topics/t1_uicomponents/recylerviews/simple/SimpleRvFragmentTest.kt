package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.simple

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.annotation.UiThreadTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.rm.android_fundamentals.MainActivity
import com.rm.android_fundamentals.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleRvFragmentTest {

    @get: Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var scenario: FragmentScenario<SimpleRvFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Androidfundamentals)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun test_RecyclerView_IsVisible_OnTheFragment() {
        launchFragmentInContainer<SimpleRvFragment>().moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.rvSimple))
            .check(matches(isDisplayed()))
    }

    // This is a negative test that tries to scroll to a descendant that does not exit.
    @Test(expected = PerformException::class)
    fun has_NoSuch_Descendant() {
        onView(withId(R.id.rvSimple))
            .perform(RecyclerViewActions.scrollTo<SimpleRvAdapter.SimpleRvViewHolder>(hasDescendant(
                withText("not on the list"))))
    }

    @Test
    fun scrollToLastItem() {
        val lastItemIndex = TOTAL_NUMBER_OF_ITEMS
        onView(withId(R.id.rvSimple))
            .perform(RecyclerViewActions.scrollToPosition<SimpleRvAdapter.SimpleRvViewHolder>(lastItemIndex - 1))
    }

    /**
     * Test to scroll in a recycler view to an item at a fixed position and verify that
     * the element with expected text is displayed.
     */
    @Test
    fun when_Clicked_To_Specific_Item_AtAPosition_ThenItemIsDisplayed() {
        onView(withId(R.id.rvSimple))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<SimpleRvAdapter.SimpleRvViewHolder>(LIST_ITEM_INDEX, click()))

        // The expected text is displayed in two view elements after click: recycler view item row and text view
        // Multiple views can be included in allOf() to avoid ambiguous matches.
        onView(allOf(withText(ITEM.id), withId(R.id.tvClickedOnRow)))
            .check(matches(isDisplayed()))
    }


    @Test
    fun when_Clicked_To_Specific_Item_TextView_Is_Updated_With_ItemId() {
        onView(withId(R.id.rvSimple))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<SimpleRvAdapter.SimpleRvViewHolder>(LIST_ITEM_INDEX, click())
            )

        onView(withId(R.id.tvClickedOnRow))
            .check(matches(withText(ITEM.id)))
    }

    companion object {
        private const val LIST_ITEM_INDEX = 3
        val ITEM = SimpleRvItem.getSimpleRvItems()[LIST_ITEM_INDEX]
        private const val TOTAL_NUMBER_OF_ITEMS = 50
    }
}
