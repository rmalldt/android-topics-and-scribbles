package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase3

import android.os.Bundle
import androidx.activity.viewModels
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityPerformConcurrentNetworkRequestsBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.useCase3Description
import com.rm.android_fundamentals.utils.EMPTY_STRING
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class PerformConcurrentNetworkRequestsActivity : BaseActivity() {

    private val binding by lazy {
        ActivityPerformConcurrentNetworkRequestsBinding.inflate(layoutInflater)
    }

    private val viewModel: PerformConcurrentNetworkRequestsViewModel by viewModels()

    private var opStartTimeMs = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnPerformNetworkRequestSeq.setOnClickListener {
            viewModel.performSequentialNetworkRequests()
        }

        binding.btnPerformNetworkRequestCon.setOnClickListener {
            viewModel.performConcurrentNetworkRequests()
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
        opStartTimeMs = System.currentTimeMillis()
        progressBar.setVisible()
        tvDuration.text = EMPTY_STRING
        tvResult.text = EMPTY_STRING
        disableButtons()
    }


    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        enableButtons()
        progressBar.setGone()
        val duration = System.currentTimeMillis() - opStartTimeMs
        tvDuration.text = getString(R.string.duration, duration)

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
        tvDuration.setGone()
        toast(uiState.message)
        enableButtons()
    }

    private fun enableButtons() = with(binding) {
        btnPerformNetworkRequestSeq.isEnabled = true
        btnPerformNetworkRequestCon.isEnabled = true
    }

    private fun disableButtons()  = with(binding) {
        btnPerformNetworkRequestSeq.isEnabled = false
        btnPerformNetworkRequestCon.isEnabled = false
    }

    override fun getTitleToolbar() = useCase3Description
}
