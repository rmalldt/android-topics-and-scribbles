package com.rm.android_fundamentals.topics.t5_intents

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.rm.android_fundamentals.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonIntentsActivityTest {

    // Grant permission to
    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant("android.permission.CALL_PHONE")

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RedirectionIntentsActivity::class.java)

    @Before
    fun setup() {
        // Initializes intents and begins recording intents, must be called before triggering any
        // actions that call an intent that we want to verify with validation or stubbing.
        Intents.init()
    }

    @Test
    fun whenTapOnCallButton_ThenDialActivityIsLaunched() {
        // Espresso doesn't stub any intents, with below method all external intents
        // (actions invoking external app) would be stubbed/not called
        intending(not(isInternal()))    // if not internal stub it
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        val phoneNumber = Uri.parse("tel:$VALID_PHONE_NUMBER")

        // Tap on the button
        onView(withId(R.id.btnCall)).perform(click())

        // Verify an intent is called with action
        intended(allOf(hasAction(Intent.ACTION_DIAL), hasData(phoneNumber)))
    }

    @Test
    fun whenTapOnSendEmailButton_ThenDialActivityIsLaunched() {
        // Tap on the button
        onView(withId(R.id.btnEmailAttach)).perform(click())

        // Verify an intent is called with action
        val expectedIntent = allOf(
            hasAction(Intent.ACTION_SEND),
            hasExtra(Intent.EXTRA_EMAIL, arrayOf("jim@in.com")),
            hasExtra(Intent.EXTRA_SUBJECT, "Email subject"),
            hasExtra(Intent.EXTRA_TEXT, "Email message text"),
            hasExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/email/attachment"))
        )
        intended(expectedIntent)
    }

    @After
    fun teardown() {
        // Clears intent state, must be called after each test case.
        Intents.release()
    }

    companion object {
        const val VALID_PHONE_NUMBER = "55512345"
    }
}
