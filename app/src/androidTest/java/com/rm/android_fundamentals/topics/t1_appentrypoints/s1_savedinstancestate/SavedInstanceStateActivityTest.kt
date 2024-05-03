package com.rm.android_fundamentals.topics.t1_appentrypoints.s1_savedinstancestate

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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SavedInstanceStateActivityTest {

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(SavedInstanceStateActivity::class.java)

    @Test
    fun onButtonClicked_should_show_text() {
        // Type something and press the button
        onView(withId(R.id.txtTypeSomething)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard())

        onView(withId(R.id.btnSubmit)).perform(click())

        // Check the text changed
        onView(withId(R.id.txtInfo)).check(matches(withText(STRING_TO_BE_TYPED)))
    }

    companion object {
        const val STRING_TO_BE_TYPED = "Espresso"
    }
}