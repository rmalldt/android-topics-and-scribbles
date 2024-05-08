package com.rm.android_fundamentals.topics.t5_intents

import android.Manifest
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

        startActivityWithExplicitIntent()
        startDifferentAppWithExplicitIntent()
        startActivityWithImplicitIntent()
        launchNotification()

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

    private fun startActivityWithExplicitIntent() {
        binding.btnExplicitIntent.setOnClickListener {
            val intent = Intent(this, IntentResultActivity::class.java) // explicit activity component specified
            startActivity(intent)
        }
    }

    private fun startDifferentAppWithExplicitIntent() {
        binding.btnExplicitYoutube.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_MAIN               // start MainActivity of target app
                `package` = "com.google.android.youtube"  // explicit pkg name specified
            }

            try { // check if target app is installed, then only launch the app
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun startActivityWithImplicitIntent() {
        binding.btnImplicitIntent.setOnClickListener {
            val sendIntent = Intent().apply {// no explicit activity component specified
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "From IntentActivity to IntentResultActivity implicit.")

            }

            /**
             * Display a dialog with lit of apps that can respond to this Intent type.
             * The list will also contain this app as the IntentResultActivity is
             * configured with Intent-filter to handle this Intent type. So, upon choosing
             * this app will start IntentResultActivity.
             */
            val title = ""
            val chooser: Intent = Intent.createChooser(sendIntent, title)
            startActivity(chooser)
        }
    }

    private val activityResultLauncherForPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                toast("Permission granted!")
            } else {
                toast("Permission denied!")
            }
        }


    private val activityResultLauncherForMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
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
        // Create intent with the explicit Activity component class name
        // that is opened when the Notification is clicked
        val intent = Intent(applicationContext, IntentResultActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("Notification", "Data from Notification")

        // Create pending intent, wrap the existing intent and set notification with pending intent
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            70,
            intent,
            PendingIntent.FLAG_IMMUTABLE // the intent arguments added  above will be ignored & not delivered
        )

        // Create notification
        val builder = NotificationCompat.Builder(this, CH_ID)
        builder.setSmallIcon(R.drawable.notification_alert)
            .setContentTitle("Notification Title")
            .setContentText("Content of the notification")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        // Get notification manager, create notification channel and launch the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CH_ID,
                "ch_notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "ch_description"
                lightColor = Color.GREEN
                enableVibration(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            manager.notify(NOTIFICATION_ID, builder.build())
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
        const val CH_ID = "CH01"
        const val NOTIFICATION_ID = 70
        const val REQUEST_CODE = 70
    }
}
