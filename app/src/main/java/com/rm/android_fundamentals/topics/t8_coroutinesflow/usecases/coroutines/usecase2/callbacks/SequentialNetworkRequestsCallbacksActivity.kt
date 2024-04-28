package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase2.callbacks

import android.os.Bundle
import androidx.activity.viewModels
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivitySequentialNetworkRequestsBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.useCase2UsingCallbacksDescription
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class SequentialNetworkRequestsCallbacksActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySequentialNetworkRequestsBinding.inflate(layoutInflater)
    }

    private val viewModel: SequentialNetworkRequestsCallbacksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnPerformNetworkRequest.setOnClickListener {
            viewModel.performSequentialNetworkRequest()
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> onLoad()
            is UiState.Success -> onSuccess(uiState)
            is UiState.Error -> onError(uiState)
        }
    }

    private fun onLoad() = with(binding) {
        progressBar.setVisible()
        tvResult.text = ""
        btnPerformNetworkRequest.isEnabled= false
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        tvResult.text = fromHtml(
            "<b>Features of most recent Android Version \" ${uiState.versionFeatures.androidVersion.name} \"</b><br>" +
                    uiState.versionFeatures.features.joinToString(
                        prefix = "- ",
                        separator = "<br>- "
                    )
        )
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        toast(uiState.message)
    }

    override fun getTitleToolbar() = useCase2UsingCallbacksDescription
}