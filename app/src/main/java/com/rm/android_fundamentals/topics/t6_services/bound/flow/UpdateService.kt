package com.rm.android_fundamentals.topics.t6_services.bound.flow

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber


class UpdateService : Service() {

    private val binder = MBinder()

    fun getProgress(): Flow<Int> {
        var progress = 0

        return flow {
            while (progress < 100) {
                progress += 1
                delay(10)
                emit(progress)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class MBinder : Binder() {
        fun getService(): UpdateService = this@UpdateService
    }
}