package com.rm.android_fundamentals.topics.t5_intents

import android.content.Intent
import android.os.Bundle
import com.rm.android_fundamentals.databinding.ActivityIntentResultBinding
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.utils.toast

class IntentResultActivity : BaseActivity() {

    private lateinit var binding: ActivityIntentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveImplicitIntent()

        receiveIntentFromNotification()

        receiveSendIntentForImage()
    }

    private fun receiveImplicitIntent() {
        if (intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain") {
            intent.extras?.let {
                val data = it.getString(Intent.EXTRA_TEXT)
                val message = "Implicit intent from IntentActivity: $data"
                binding.txtFromImplicitIntent.text = message
            }
        }
    }

    private fun receiveIntentFromNotification() {
        intent.extras?.let {
            val data = it.getString("Notification")
            val message = "Intent from Notification: $data"
            binding.txtFromNotification.text = message
        }
    }

    private fun receiveSendIntentForImage() {

    }

    override fun getTitleToolbar() = "Intent Result Activity"

}