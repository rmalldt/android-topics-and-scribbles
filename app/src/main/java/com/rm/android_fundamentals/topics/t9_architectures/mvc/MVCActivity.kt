package com.rm.android_fundamentals.topics.t9_architectures.mvc

import android.os.Bundle
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.base.mvcSubTopic1
import com.rm.android_fundamentals.databinding.ActivityMvcactivityBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t9_architectures.common.VersionUiState
import com.rm.android_fundamentals.utils.EMPTY_STRING
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class MVCActivity : BaseActivity() {

    private lateinit var binding: ActivityMvcactivityBinding
    private lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = Controller(this)

        binding.btnPerformNetworkRequest.setOnClickListener {
            controller.performRequest()
        }
    }

    fun render(uiState: VersionUiState) {
        when (uiState) {
            is VersionUiState.Loading -> onLoad()
            is VersionUiState.Error -> onError(uiState.message)
            is VersionUiState.Success -> onSuccess(uiState.recentVersions)
        }
    }

    fun onSuccess(versionList: List<AndroidVersion>) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        val formatted = versionList.map { "API ${it.apiLevel}: ${it.name}" }
        binding.tvResult.text = fromHtml(
            "<b>Recent Android Versions</b><br>${formatted.joinToString(separator = "<br>")}"
        )
    }

    fun onLoad() = with(binding) {
        progressBar.setVisible()
        tvResult.text = EMPTY_STRING
        btnPerformNetworkRequest.isEnabled = false
    }

    fun onError(message: String) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        toast(message)
    }

    override fun onPause() {
        super.onPause()
        controller.onClear()
    }

    override fun onDestroy() {
        controller.onDestroy()
        super.onDestroy()
    }

    override fun getTitleToolbar(): String = mvcSubTopic1
}