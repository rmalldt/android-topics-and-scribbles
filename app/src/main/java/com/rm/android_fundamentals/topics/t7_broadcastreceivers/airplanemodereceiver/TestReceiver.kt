package com.rm.android_fundamentals.topics.t7_broadcastreceivers.airplanemodereceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class TestReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "TEST_ACTION") {
            Timber.d("BroadcastTest: received test intent")
        }
    }
}