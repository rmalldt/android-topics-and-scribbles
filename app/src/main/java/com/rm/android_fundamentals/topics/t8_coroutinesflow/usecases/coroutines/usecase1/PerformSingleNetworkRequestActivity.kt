package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase1

import android.os.Bundle
import androidx.activity.viewModels
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityPerformSingleNetworkRequestBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.useCase1Description
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast
import kotlinx.coroutines.runBlocking

class PerformSingleNetworkRequestActivity : BaseActivity() {

    private lateinit var binding: ActivityPerformSingleNetworkRequestBinding
    private val viewModel: PerformSingleNetworkRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerformSingleNetworkRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnPerformNetworkRequest.setOnClickListener {
            viewModel.performSingleNetworkRequest()
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
        val readableVersions = uiState.recentVersion.map { "API ${it.apiLevel}: ${it.name}" }
        tvResult.text = fromHtml(
            "<b>Recent Android Version</b><br>${readableVersions.joinToString(separator = "<br>")}"
        )
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        toast(uiState.message)
    }

    override fun getTitleToolbar() = useCase1Description

    private fun runOnMainThreadTest() {
        runBlocking {
            viewModel.performRequestOnMainThread()
        }
    }
}