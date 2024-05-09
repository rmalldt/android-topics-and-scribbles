package com.rm.android_fundamentals.topics.t7_broadcastreceivers.airplanemodereceiver

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentBroadcastReceiverBinding

class BroadcastReceiverFragment : Fragment() {

    private var _binding: FragmentBroadcastReceiverBinding? = null
    private val binding get() = _binding!!

    private val airPlaneModeReceiver = AirPlaneModeReceiver {
        onAirPlaneModeOn(it)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBroadcastReceiverBinding.inflate(inflater, container, false)

        /**
         * Registering receiver creates a dynamic receiver i.e. only
         * when this app is active, it will receive the broadcast intent,
         * when the app is not active, it won't receive the broadcast.
         */
        requireActivity().registerReceiver(
            airPlaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )

        return binding.root
    }

    private fun onAirPlaneModeOn(isTurnedOn: Boolean) {
        if (isTurnedOn) {
            binding.txtBroadcastReceiverAirplaneMode.text = getString(R.string.isTrue)
        } else {
            binding.txtBroadcastReceiverAirplaneMode.text = getString(R.string.isFalse)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver for AirPlaneModeReceiver
        requireActivity().unregisterReceiver(airPlaneModeReceiver)
    }
}