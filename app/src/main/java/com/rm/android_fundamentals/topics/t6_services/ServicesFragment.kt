package com.rm.android_fundamentals.topics.t6_services

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.rm.android_fundamentals.databinding.FragmentServicesBinding
import com.rm.android_fundamentals.topics.t6_services.background.MBackgroundService
import com.rm.android_fundamentals.topics.t6_services.foreground.MForegroundService
import com.rm.android_fundamentals.utils.toast

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activityResultLauncherForPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.btnStartBackgroundService.setOnClickListener {
            val intent = Intent(requireContext(), MBackgroundService::class.java)
            requireActivity().startService(intent)
        }

        binding.btnStartRun.setOnClickListener {
            val intent = Intent(requireContext(), MForegroundService::class.java).apply {
                action = MForegroundService.Actions.START.toString()
            }
            requireActivity().startService(intent)
        }

        binding.btnStopRun.setOnClickListener {
            val intent = Intent(requireContext(), MForegroundService::class.java).apply {
                action = MForegroundService.Actions.STOP.toString()
            }
            requireActivity().startService(intent)
        }
    }

    private val activityResultLauncherForPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                requireActivity().toast("Permission granted!")
            } else {
                requireActivity().toast("Permission denied!")
            }
        }
}