package com.rm.android_fundamentals.topics.t6_services.bound.apicall

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentBoundServiceBinding
import com.rm.android_fundamentals.mocknetwork.mockflow.Stock
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat


class BoundServiceFragment : Fragment() {

    private var _binding: FragmentBoundServiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var mService: FetchService
    private var isBound = false

    private val dataAdapter by lazy { DataAdapter() }


    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as FetchService.FetchServiceBinder
            mService = binder.getFetchService()
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
        _binding = FragmentBoundServiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            FetchEventBus.events.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.tvLastUpdateBoundService.text =
                        getString(
                            R.string.lastupdatetime,
                            LocalDateTime.now().toString(DateTimeFormat.fullTime())
                        )
                    dataAdapter.stockList = (it as StockEvent).data
                }
        }
    }

    private fun initRecyclerView() {
        binding.rvBoundService.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dataAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), FetchService::class.java).apply {
            requireActivity().bindService(this, mConnection, Context.BIND_AUTO_CREATE)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unbindService(mConnection)
        _binding = null
    }
}