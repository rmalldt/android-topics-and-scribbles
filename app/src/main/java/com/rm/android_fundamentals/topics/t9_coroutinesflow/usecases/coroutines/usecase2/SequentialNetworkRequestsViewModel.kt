package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.mocknetwork.mock.MockApi
import kotlinx.coroutines.launch

class SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSequentialNetworkRequests() {
        _uiState.value = UiState.Loading

        /**
         * Note: Code inside the viewModelScope is executed in the MainThread.
         * This might lead to confusion regarding the network requests done
         * in the viewModelScope might block the MainThread.
         *
         * But notice getRecentAndroidVersions() and getRecentAndroidVersionFeatures()
         * are suspend functions which can be suspended/paused and resumed.
         * When suspend functions are used correctly even inside the MainThread,
         * they are non-blocking. Hence the MainThread is not blocked.
         */
        viewModelScope.launch {
            // Lines of code inside "launch" are executed in order/sequence
            try {
                // Call 1
                val recentVersion = mockApi.getRecentAndroidVersions()
                val mostRecentVersion = recentVersion.last()

                // Call 2
                val versionFeatures = mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)

                _uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }
}