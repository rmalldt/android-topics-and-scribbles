package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase4

import android.os.Bundle
import androidx.activity.viewModels
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityTimeoutAndRetryBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.useCase4Description
import com.rm.android_fundamentals.utils.EMPTY_STRING
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class TimeoutAndRetryActivity : BaseActivity() {

    private val binding by lazy { ActivityTimeoutAndRetryBinding.inflate(layoutInflater) }
    private val viewModel: TimeoutAndRetriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnTimeout.setOnClickListener {
            val timeOut = binding.editTxtTimeout.text.toString().toLongOrNull()
            if (timeOut != null) {
                viewModel.performNetworkRequestWithTimeout(timeOut)
            }
        }

        binding.btnRetry.setOnClickListener {
            viewModel.performNetworkRequestWithRetries()
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onLoad() = with(binding) {
        progressBar.setVisible()
        tvResult.text = EMPTY_STRING
        disableButtons()
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        enableButtons()
        val readableVersions = uiState.recentVersions.map { "API ${it.apiLevel}: ${it.name}" }
        tvResult.text = fromHtml(
            "<b>Recent Android Versions</b><br>${readableVersions.joinToString(separator = "<br>")}"
        )
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        enableButtons()
        toast(uiState.message)
    }

    private fun enableButtons() = with(binding) {
        btnTimeout.isEnabled = true
        btnRetry.isEnabled = true
    }

    private fun disableButtons() = with(binding) {
        btnTimeout.isEnabled = false
        btnRetry.isEnabled = false
    }

    override fun getTitleToolbar() = useCase4Description
}