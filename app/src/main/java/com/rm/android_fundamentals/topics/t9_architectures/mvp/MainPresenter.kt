package com.rm.android_fundamentals.topics.t9_architectures.mvp

import com.rm.android_fundamentals.topics.t9_architectures.common.RxMockApi
import com.rm.android_fundamentals.topics.t9_architectures.common.VersionUiState
import com.rm.android_fundamentals.topics.t9_architectures.common.rxMockApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter(
    view: MainContract.View,
    private val rxMockApi: RxMockApi = rxMockApi()
) : MainContract.Presenter {

    private var view: MainContract.View? = view

    private val disposables = CompositeDisposable()

    override fun onViewCreated() {
        TODO("Not yet implemented")
    }

    override fun onLoadDataClicked() {
        performRequest()
    }

    override fun onPause() {
        onClear()
    }

    override fun onDestroy() {
        Timber.d("mvc: Presenter onDestroy called")
        view = null
    }

    private fun performRequest() {
        view?.render(VersionUiState.Loading)
        rxMockApi.getRecentAndroidVersions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    view?.render(VersionUiState.Error("Something went wrong"))
                },
                onSuccess = { versionList ->
                    view?.render(VersionUiState.Success(versionList))
                }
            )
            .addTo(disposables)
    }

    private fun onClear() {
        Timber.d("mvp: Presenter onClear called")
        disposables.clear()
    }
}