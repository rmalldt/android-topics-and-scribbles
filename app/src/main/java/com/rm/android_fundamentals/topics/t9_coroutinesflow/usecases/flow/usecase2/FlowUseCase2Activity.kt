package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityFlowUseCase1Binding
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.flowUseCase4Description
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class FlowUseCase2Activity : BaseActivity() {

    private val binding by lazy { ActivityFlowUseCase1Binding.inflate(layoutInflater) }

    private var stockListAdapter = StockListAdapter()

    private val viewModel: FlowUseCase2ViewModel by viewModels {
        ViewModelFactory(
            NetworkStockPriceDataSource(flowMockApi(applicationContext)),
            Dispatchers.Default
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()

        useFlowWithLifecycle()
        //useRepeatOnLifecycle()
    }

    private fun useFlowWithLifecycle() {
        lifecycleScope.launch {
            viewModel.currentStockPriceAsFlow
                // Collection starts when the activity reaches STARTED state
                // and stops when the activity falls below STARTED state i.e. STOPPED state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { uiState ->
                    render(uiState)
                }
        }
    }

    private fun useRepeatOnLifecycle() {
        lifecycleScope.launch {
            // Collection starts when the activity reaches STARTED state
            // and stops when the activity falls below STARTED state i.e. STOPPED state
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentStockPriceAsFlow.collect { uiState ->
                    render(uiState)
                }
            }
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                binding.progressBar.setVisible()
                binding.rv.setGone()
            }
            is UiState.Success -> {
                binding.rv.setVisible()
                binding.tvLastUpdateTime.text =
                    "lastUpdateTime: ${LocalDateTime.now().toString(DateTimeFormat.fullTime())}"
                stockListAdapter.stockList = uiState.stockList
                binding.progressBar.setGone()
            }
            is UiState.Error -> {
                toast(uiState.message)
                binding.progressBar.setGone()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            adapter = stockListAdapter
            layoutManager = LinearLayoutManager(this@FlowUseCase2Activity)
        }
    }

    override fun getTitleToolbar() = flowUseCase4Description
}