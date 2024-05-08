package com.rm.android_fundamentals.topics.t8_broadcastreceivers.airplanemodereceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.rm.android_fundamentals.utils.toast

class AirPlaneModeReceiver(val onAirplaneModeOn: (isTurnedOn: Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Check the intent
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {

            // Check if the airplane mode is turned on in the settings
            val isTurnedOn = Settings.Global.getInt(context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON) != 0
            onAirplaneModeOn(isTurnedOn)
        }
    }
}