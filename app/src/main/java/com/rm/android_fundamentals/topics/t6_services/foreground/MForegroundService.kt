package com.rm.android_fundamentals.topics.t6_services.foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.rm.android_fundamentals.R

class MForegroundService : Service() {

    /**
     * This method is triggered when another android component sends Intent
     * to start this service.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.notification_alert)
            .setContentTitle("Foreground is active.")
            .setContentText("Current time: ${System.currentTimeMillis()}")
            .build()

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            NOTIFICATION_CHANNEL,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        startForeground(10, notification)
    }

    /**
     * Used to create bound service.
     * Common use-case is when multiple apps need to connect to this single service.
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    enum class Actions {
        START, STOP
    }

    companion object {
        const val NOTIFICATION_CHANNEL = "running_channel"
    }
}