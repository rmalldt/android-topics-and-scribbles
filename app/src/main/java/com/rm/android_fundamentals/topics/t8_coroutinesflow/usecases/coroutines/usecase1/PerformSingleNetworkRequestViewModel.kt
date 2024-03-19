package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase1

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                _uiState.value = UiState.Success(recentAndroidVersions)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }

    /**
     * Test to observe how UI thread is freezes when the network request
     * is made on the MainThread
     */
    @MainThread
    suspend fun performRequestOnMainThread() {
        _uiState.value = UiState.Loading
        val result = mockApi.getRecentAndroidVersions()
        _uiState.value = UiState.Success(result)
    }
}