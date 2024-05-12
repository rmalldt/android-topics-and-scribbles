package com.rm.android_fundamentals.topics.t6_services.bound.simple

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rm.android_fundamentals.databinding.FragmentSimpleBoundServiceBinding
import com.rm.android_fundamentals.utils.toast

class SimpleBoundServiceFragment : Fragment() {

    private var _binding: FragmentSimpleBoundServiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var mService: MBoundService
    private var isBound = false

    private val mConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MBoundService.LocalBinder
            mService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpleBoundServiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGetRandomSimpleBoundService.setOnClickListener {
            if (isBound) {
                val random = mService.getRandomNumber()
                requireActivity().toast("Number: $random")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), MBoundService::class.java).apply {
            requireActivity().bindService(this, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(mConnection)
        isBound = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}