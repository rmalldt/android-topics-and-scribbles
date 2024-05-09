package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.MockApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import timber.log.Timber

class TimeoutWithRetriesViewModel(
    private val mockApi: MockApi = mockApiError()
) : BaseViewModel<UiState>() {

    fun multipleNetworkRequestsWithRetries() {
        _uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        viewModelScope.launch {
            // Get versions
            var versions: List<AndroidVersion> = listOf()
            supervisorScope {
                try {
                    versions = async {
                        retryWithTimeout(numberOfRetries, timeout) {
                            mockApi.getRecentAndroidVersions()
                        }
                    }.await() // must catch exception, await is called directly in supervisorScope
                } catch (e: Exception) {
                    if (e is CancellationException) {
                        throw e // rethrow, otherwise Cancellation exception is ignored
                    }
                    if (e is HttpException) {
                        _uiState.value = UiState.Error("Internal server error: failed to fetch versions!")
                    } else {
                        _uiState.value = UiState.Error("Network error: failed to fetch versions!")
                    }
                }
            }

            // Then get features
            if (versions.isNotEmpty()) {
                supervisorScope {
                    val deferred = versions
                        .map { version ->
                            // Multiple calls run in parallel
                            async {
                                retryWithTimeout(numberOfRetries, timeout) {
                                    mockApi.getAndroidVersionFeatures(version.apiLevel)
                                }
                            }
                        }

                    val featuresList = deferred
                        .mapNotNull {
                            try {
                                it.await()
                            } catch (e: Exception) {
                                if (e is CancellationException) {
                                    throw e // rethrow, otherwise Cancellation exception is ignored
                                }
                                Timber.d("Error loading version data")
                                null// null value for network call that threw exception
                            }
                    }

                    if (featuresList.isNotEmpty()) {
                        // If out of all network calls all or some succeeds, display values
                        _uiState.value = UiState.Success(featuresList)
                    } else {
                        // If all network calls fails, display error message
                        _uiState.value = UiState.Error("Network error: failed to fetch versions")
                    }
                }
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