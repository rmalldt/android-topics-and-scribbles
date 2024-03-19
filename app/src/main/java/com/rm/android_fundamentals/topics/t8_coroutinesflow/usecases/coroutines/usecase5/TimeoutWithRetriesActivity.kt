package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase5

import android.os.Bundle
import androidx.activity.viewModels
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityTimeoutWithRetriesBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.useCase5Description
import com.rm.android_fundamentals.utils.EMPTY_STRING
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class TimeoutWithRetriesActivity : BaseActivity() {

    private val binding by lazy {
        ActivityTimeoutWithRetriesBinding.inflate(layoutInflater)
    }

    private val viewModel: TimeoutWithRetriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnPerformNetworkRequest.setOnClickListener {
            val timeOut = binding.editTxtTimeOut.text.toString().toLongOrNull()
            if (timeOut != null) {
                viewModel.multipleNetworkRequestsWithRetries()
            }
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
        btnPerformNetworkRequest.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true

        val versionFeaturesList = uiState.versionFeaturesList

        val versionFeaturesString = versionFeaturesList.joinToString(separator = "<br><br>") {
            "<b>New Features of ${it.androidVersion.name}</b><br>${it.features.joinToString(
                prefix = "-",
                separator = "<br>-"
            )}"
        }

        tvResult.text = fromHtml(versionFeaturesString)
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        toast(uiState.message)
    }

    override fun getTitleToolbar() = useCase5Description
}