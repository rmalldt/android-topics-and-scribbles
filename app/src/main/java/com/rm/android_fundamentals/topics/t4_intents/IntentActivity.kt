package com.rm.android_fundamentals.topics.t4_intents

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.rm.android_fundamentals.MainActivity
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityIntentBinding
import com.rm.android_fundamentals.utils.toastMessage

class IntentActivity : BaseActivity() {

    private lateinit var binding: ActivityIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askNotificationPermission()

        gotoMainActivityExplicit()
        gotoMainActivityImplicit()

        launchNotification()

        binding.btnCommonIntents.setOnClickListener {
            val intent = Intent(this, CommonIntentsActivity::class.java)
            startActivity(intent)
        }
    }

    // Send explicit intent
    private fun gotoMainActivityExplicit() {
        binding.btnExplicitIntent.setOnClickListener {
            // Explicit activity component class name passed to the intent
            val intent = Intent(this, IntentResultActivity::class.java)
            startActivity(intent)
        }
    }

    // Send implicit intent
    private fun gotoMainActivityImplicit() {
        // No explicit component class name in the intent
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Go to IntentResultActivity implicit")
            type = "text/plain"
        }

        // Create a choose for the intent and display a dialog with lit of apps
        // that respond to the intent
        val title = ""
        val chooser: Intent = Intent.createChooser(sendIntent, title)

        binding.btnImplicitIntent.setOnClickListener {
            startActivity(chooser)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this@IntentActivity, android.Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED) {
                // Ask permission for Post notification if it is not already granted
                ActivityCompat.requestPermissions(this@IntentActivity,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE)
            }
        }
    }

    private fun launchNotification() {
        binding.btnNotification.setOnClickListener {
            makeNotification()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun makeNotification() {
        // Create notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.notification_alert)
            .setContentTitle("Notification title")
            .setContentText("Some text for the notification")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Create intent with the explicit Activity component class name
        // that is opened when the Notification is clicked
        val intent = Intent(applicationContext, IntentResultActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(DATA_KEY, "Data value passed through notification")

        // Create pending intent, wrap the existing intent and set notification with pending intent
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pendingIntent)

        // Get notification manager, create notification channel and launch the notification
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "channel_01"
            val channelDescription = "Channel description"
            val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                description = channelDescription
                lightColor = Color.GREEN
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }
        manager.notify(0, builder.build())
    }

    override fun getTitleToolbar(): String = "Intent activity"

    companion object {
        const val CHANNEL_ID = "CH_01"
        const val DATA_KEY ="data"
        const val REQUEST_CODE = 70
    }
}
