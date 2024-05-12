package com.rm.android_fundamentals.topics.t6_services.bound.simple

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.rm.android_fundamentals.utils.toast
import kotlin.random.Random

class MBoundService : Service() {

    // Binder for client
    private val binder = LocalBinder()

    // Method accessible to clients
    fun getRandomNumber(): Int {
        return Random.nextInt(0, 100)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    /**
     * Client and this service run in the same process.
     * Provide client with the instance of Binder to access
     * Binder methods and service methods.
     */
    inner class LocalBinder : Binder() {

        fun getService(): MBoundService = this@MBoundService
    }

    override fun onDestroy() {
        toast("unbinding service")
    }
}