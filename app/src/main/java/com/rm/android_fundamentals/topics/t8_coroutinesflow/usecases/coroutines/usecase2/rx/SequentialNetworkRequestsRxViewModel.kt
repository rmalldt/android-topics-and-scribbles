package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase2.rx

import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private val disposables = CompositeDisposable()

    fun performSequentialNetworkRequest() {
        _uiState.value = UiState.Loading

        // Call 1
        mockApi.getRecentAndroidVersions()
            .flatMap { androidVersions ->
                val recentVersion = androidVersions.last()

                // Call 2
                mockApi.getAndroidVersionFeatures(recentVersion.apiLevel)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { featureVersions ->
                    _uiState.value = UiState.Success(featureVersions)
                },
                onError = {
                    _uiState.value = UiState.Error("Network Request failed.")
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}