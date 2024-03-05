package com.rm.android_fundamentals.topics.t4_intents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityIntentResultBinding

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
        Toast.makeText(this, "Message: $extras", Toast.LENGTH_SHORT).show()
    }

    private fun getDataFromNotification() {
        val data = intent.getStringExtra(IntentActivity.DATA_KEY)
        binding.txtNotification.text = data
    }

    override fun getTitleToolbar() = "Intent result activity"
}