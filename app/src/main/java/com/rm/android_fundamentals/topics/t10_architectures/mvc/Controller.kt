package com.rm.android_fundamentals.topics.t10_architectures.mvc

import com.rm.android_fundamentals.topics.t10_architectures.common.RxMockApi
import com.rm.android_fundamentals.topics.t10_architectures.common.VersionUiState
import com.rm.android_fundamentals.topics.t10_architectures.common.rxMockApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class Controller(
    view: MVCActivity,
    val mockApi: RxMockApi = rxMockApi()
) {
    private val disposables = CompositeDisposable()

    var view: MVCActivity? = view

    fun performRequest() {
        view?.render(VersionUiState.Loading)
        mockApi.getRecentAndroidVersions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    view?.render(VersionUiState.Error("Something went wrong"))
                },
                onSuccess = { featureList ->
                    view?.render(VersionUiState.Success(featureList))
                }
            )
            .addTo(disposables)
    }

    fun onClear() {
        Timber.d("mvc: Controller onClear called")
        disposables.clear()
    }

    fun onDestroy() {
        Timber.d("mvc: Controller onDestroy called")
        this.view = null
    }
}