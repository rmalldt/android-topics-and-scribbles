package com.rm.android_fundamentals.topics.t1_appentrypoints.s2_passdatabetweenactivities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.topics.t1_appentrypoints.s1_savedinstancestate.SavedInstanceStateActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResultActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(ResultActivity::class.java)

    @Test
    fun onButtonClicked_should_navigate_to_ResultProducingActivity() {
        // Type text and press the button.
        onView(withId(R.id.editTxtInput)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard())

        onView(withId(R.id.btnPassObject)).perform(click())

        // This view is in a different Activity, no need to tell Espresso
        // No need to specify Activity, just specify the View id and the Espresso will find itself.
        onView(withId(R.id.txtReceivedData)).check(matches(withText(STRING_TO_BE_TYPED)))
    }

    companion object {
        const val STRING_TO_BE_TYPED = "Espresso"
    }
}