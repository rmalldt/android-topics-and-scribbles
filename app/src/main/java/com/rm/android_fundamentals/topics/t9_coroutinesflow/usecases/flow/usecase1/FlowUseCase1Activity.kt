package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityFlowUseCase1Binding
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.flowUseCase1Description
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast
import kotlinx.coroutines.Dispatchers
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class FlowUseCase1Activity : BaseActivity() {

    private val binding by lazy { ActivityFlowUseCase1Binding.inflate(layoutInflater) }

    private var stockListAdapter = StockListAdapter()

    private val viewModel: FlowUseCase1ViewModel by viewModels {
        ViewModelFactory(
            NetworkStockPriceDataSource(flowMockApiWithError(applicationContext)),
            Dispatchers.Default
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()

        viewModel.currentStockPriceAsLiveData.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
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
            layoutManager = LinearLayoutManager(this@FlowUseCase1Activity)
            //addItemDecoration(initItemDecorator(this@FlowUseCase1Activity))
        }
    }

    override fun getTitleToolbar() = flowUseCase1Description
}