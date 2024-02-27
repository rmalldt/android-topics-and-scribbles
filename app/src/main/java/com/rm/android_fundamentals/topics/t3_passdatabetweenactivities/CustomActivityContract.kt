package com.rm.android_fundamentals.topics.t3_passdatabetweenactivities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.rm.android_fundamentals.utils.toastMessage

class CustomActivityContract : ActivityResultContract<String, Int?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, ResultProducingActivity::class.java).apply {
            toastMessage(context, "Starting activity for result with $input")
            putExtra(ResultActivity.RESULT_KEY, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? {
        return if (resultCode == Activity.RESULT_OK) {
            intent?.getIntExtra(ResultActivity.RESULT_KEY, 0)
        } else {
            0
        }
    }
}