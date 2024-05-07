package com.rm.android_fundamentals.topics.t5_intents

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.ActivityIntentBinding
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.utils.toast

class IntentActivity : BaseActivity() {

    private lateinit var binding: ActivityIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //askNotificationPermission()

        gotoMainActivityExplicit()
        gotoMainActivityImplicit()

        launchNotification()

        binding.btnCommonIntents.setOnClickListener {
            val intent = Intent(this, CommonIntentsActivity::class.java)
            startActivity(intent)
        }

        binding.btnRedirectionIntents.setOnClickListener {
            val intent = Intent(this, RedirectionIntentsActivity::class.java)
            startActivity(intent)
        }

        binding.btnPermissions.setOnClickListener {
            activityResultLauncherForPermission.launch(Manifest.permission.READ_CONTACTS)
        }

        binding.btnMultiplePermissions.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                activityResultLauncherForMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_AUDIO
                    )
                )
            }
        }

        binding.btnRequestPermissionDeprecated.setOnClickListener {
            askNotificationPermission()
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

    private val activityResultLauncherForPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            if (isGranted) {
                toast("Permission granted!")
            } else {
                toast("Permission denied!")
            }
        }


    private val activityResultLauncherForMultiplePermissions =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    toast("All permission granted!")
                } else {
                    toast("Permissions denied!")
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
        builder.setContentIntent(pendingIntent) // attach notification to pending intent

        // Get notification manager, create notification channel and launch the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "channel_01"
            val channelDescription = "channel description"
            val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                description = channelDescription
                lightColor = Color.GREEN
                enableVibration(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            manager.notify(0, builder.build())
        }
    }


    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this@IntentActivity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toast("Permission granted!")
                } else {
                    toast("Permission denied!")
                }
                return
            }
        }
    }

    override fun getTitleToolbar(): String = "Intent Activity"

    companion object {
        const val CHANNEL_ID = "CH_01"
        const val DATA_KEY ="data"
        const val REQUEST_CODE = 70
    }
}
