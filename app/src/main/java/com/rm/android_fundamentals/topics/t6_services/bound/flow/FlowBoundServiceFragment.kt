package com.rm.android_fundamentals.topics.t6_services.bound.flow

import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentFlowBoundServiceBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowBoundServiceFragment : Fragment() {

    private var _binding: FragmentFlowBoundServiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var boundService: UpdateService
    private var bound: Boolean = false

    // Callback that creates connection between this client and service
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // Bound to UpdateService, cast the IBinder and get UpdateService instance
            val binder = service as UpdateService.MBinder
            boundService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlowBoundServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonClick()
    }

    private fun onButtonClick() {
        binding.btnDownloadBoundService.setOnClickListener {
            if (bound) {
                binding.progBarBoundService.apply {
                    progress = 0
                    max = 100
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    boundService.getProgress()
                        .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                        .collect {
                            binding.progBarBoundService.progress = it
                            binding.txtProgressBoundService.text =
                                getString(R.string.progress_percent, it.toString())
                        }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("OnStart called, binding service...")
        // Bind to UpdateService
        val intent = Intent(requireContext(), UpdateService::class.java)
        requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(serviceConnection)
        bound = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
