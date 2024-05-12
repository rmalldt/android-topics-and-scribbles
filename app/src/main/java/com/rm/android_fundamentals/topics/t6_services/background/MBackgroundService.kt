package com.rm.android_fundamentals.topics.t6_services.background

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.rm.android_fundamentals.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MBackgroundService : Service() {

    val job = SupervisorJob()
    val scope = CoroutineScope(job + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        toast("starting background service with id: $startId")
        scope.launch {
            repeat(10) {
                delay(1000)
                Timber.d("background service started: $it")
            }
            stopSelf(startId)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        toast("background service done")
        job.cancel()
    }
}