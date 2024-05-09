package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class TimeoutAndRetriesViewModel(
    private val mockApiTimeout: MockApi = mockApiTimeOut(),
    private val mockApiRetries: MockApi = mockApiRetries()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestWithTimeout(timeOut: Long) {
        //usingTimeout(timeOut)
        usingTimeoutOrNull(timeOut)
    }

    private fun usingTimeout(timeOut: Long) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val versions = withTimeout(timeOut) {
                    mockApiTimeout.getRecentAndroidVersions()
                }
                _uiState.value = UiState.Success(versions)
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                _uiState.value = UiState.Error("Network Request timed out")
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    private fun usingTimeoutOrNull(timeOut: Long) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val versions = withTimeoutOrNull(timeOut) {
                    mockApiTimeout.getRecentAndroidVersions()
                }
                if (versions != null) {
                    _uiState.value = UiState.Success(versions)
                } else {
                    _uiState.value = UiState.Error("Network Request timed out")
                }
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    fun performNetworkRequestWithRetries() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val numberOfRetries = 2
            try {
                retry(numberOfRetries) {
                    loadVersions()
                }
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed")
            }
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

    private suspend fun loadVersions() {
        val versions = mockApiRetries.getRecentAndroidVersions()
        _uiState.value = UiState.Success(versions)
    }

    fun performNetworkRequestWithRetriesSimplified() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val numberOfRetries = 2
            try {
                repeat(numberOfRetries) {
                    // This try-catch is necessary; if loadVersion throws,
                    // no retries performed as control goes to outer catch block.
                    try {
                        loadVersions()
                        return@repeat
                    } catch (e: Exception) { // if loadVersion throws,
                        Timber.e(e)          // simply log exception
                    }
                }
                loadVersions()  // last retry
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed")
            }
        }
    }
}