package com.rm.android_fundamentals.topics.t9_architectures.mvp

import com.rm.android_fundamentals.topics.t9_architectures.common.VersionUiState

interface BasePresenter {
    fun onDestroy()
}

interface BaseView<T> {
    fun setPresenter(presenter: T)
}

interface MainContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun onLoadDataClicked()
        fun onPause()
    }

    interface View : BaseView<Presenter> {
       fun render(uiState: VersionUiState)
    }
}
