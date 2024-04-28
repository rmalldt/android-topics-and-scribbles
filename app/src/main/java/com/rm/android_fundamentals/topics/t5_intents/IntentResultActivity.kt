package com.rm.android_fundamentals.topics.t5_intents

import android.content.Intent
import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityIntentResultBinding
import com.rm.android_fundamentals.utils.toast

class IntentResultActivity : BaseActivity() {

    private lateinit var binding: ActivityIntentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            println("Called: ${intent.extras}")
            receiveImplicitIntent()
        }

        getDataFromNotification()
    }

    private fun receiveImplicitIntent() {
        val extras = intent.extras?.getString(Intent.EXTRA_TEXT)
        this@IntentResultActivity.toast( "Message: $extras")
    }

    private fun getDataFromNotification() {
        val data = intent.getStringExtra(IntentActivity.DATA_KEY)
        binding.txtNotification.text = data
    }

    override fun getTitleToolbar() = "Intent Result Activity"
}