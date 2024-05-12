package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase1

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersions
import com.rm.android_fundamentals.utils.EspressoIdlingResource
import com.rm.android_fundamentals.utils.fromHtml
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerformSingleNetworkRequestActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(PerformSingleNetworkRequestActivity::class.java)

    @Before
    fun setup() {
        // Register idling resource before each test
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun when_ButtonIsClicked_ThenAPICallIsMadeToFetchData_AndTheTextViewIsUpdated() {
        onView(withId(R.id.btnPerformNetworkRequest))
            .perform(click())

        onView(withId(R.id.tvResult))
            .check(matches(withText(RESULT_TEXT.toString())))
    }

    @After
    fun teardown() {
        // Unregister idling resource after each test
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    companion object {
        val readableVersions = mockAndroidVersions.map { "API ${it.apiLevel}: ${it.name}" }
        val RESULT_TEXT = fromHtml(
            "<b>Recent Android Version</b><br>${readableVersions.joinToString(separator = "<br>")}"
        )
    }
}
