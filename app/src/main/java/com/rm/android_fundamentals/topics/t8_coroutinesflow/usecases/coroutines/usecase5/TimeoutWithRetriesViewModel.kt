package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase5

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber

class TimeoutWithRetriesViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun multipleNetworkRequestsWithRetries() {
        _uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        viewModelScope.launch {
            try {
                // Get versions
                val versions = async {
                    retryWithTimeout(numberOfRetries, timeout) {
                        mockApi.getRecentAndroidVersions()
                    }
                }.await() // suspends

                // Then get features
                val featuresList = versions
                    .map { version ->
                        // Run in parallel
                        async {
                            retryWithTimeout(numberOfRetries, timeout) {
                                mockApi.getAndroidVersionFeatures(version.apiLevel)
                            }
                        }
                    }.awaitAll() // suspend, await for the completion
                    _uiState.value = UiState.Success(featuresList)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    fun concurrentNetworkRequestsWithRetries(timeOut: Long) {
        _uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        val oreoVersionsDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                mockApi.getAndroidVersionFeatures(27)
            }
        }

        val pieVersionsDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                mockApi.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = listOf(
                    oreoVersionsDeferred,
                    pieVersionsDeferred
                ).awaitAll() // suspend, await for the completion

                _uiState.value = UiState.Success(versionFeatures)

            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    private suspend fun <T> retryWithTimeout(
        numberOfRetries: Int,
        timeOut: Long,
        block: suspend () -> T
    ): T = retry(numberOfRetries) {
        withTimeout(timeOut) {
            block()
        }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        initialDelayMillis: Long = 100,
        maxDelayMillis: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayMillis

        repeat(numberOfRetries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
        }
        return block()
    }
}