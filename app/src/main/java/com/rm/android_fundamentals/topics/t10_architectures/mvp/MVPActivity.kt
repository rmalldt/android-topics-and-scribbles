package com.rm.android_fundamentals.topics.t10_architectures.mvp

import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.legacy.mvpSubTopic2
import com.rm.android_fundamentals.databinding.ActivityMvpactivityBinding
import com.rm.android_fundamentals.mocknetwork.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t10_architectures.common.VersionUiState
import com.rm.android_fundamentals.utils.EMPTY_STRING
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class MVPActivity : BaseActivity(), MainContract.View {

    private lateinit var binding: ActivityMvpactivityBinding
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPresenter(MainPresenter(this))

        binding.btnPerformNetworkRequest.setOnClickListener {
            presenter.onLoadDataClicked()
        }
    }

    override fun render(uiState: VersionUiState) {
        when (uiState) {
            is VersionUiState.Loading -> onLoad()
            is VersionUiState.Error -> onError(uiState.message)
            is VersionUiState.Success -> onSuccess(uiState.recentVersions)
        }
    }

    fun onLoad() = with(binding) {
        progressBar.setVisible()
        tvResult.text = EMPTY_STRING
        btnPerformNetworkRequest.isEnabled = false
    }

    fun onSuccess(versionList: List<AndroidVersion>) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        val formatted = versionList.map { "API ${it.apiLevel}: ${it.name}" }
        binding.tvResult.text = fromHtml(
            "<b>Recent Android Versions</b><br>${formatted.joinToString(separator = "<br>")}"
        )
    }

    fun onError(message: String) = with(binding) {
        progressBar.setGone()
        btnPerformNetworkRequest.isEnabled = true
        toast(message)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun getTitleToolbar(): String = mvpSubTopic2

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}