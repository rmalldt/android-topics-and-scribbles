package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformConcurrentNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSequentialNetworkRequests() {
        getVersionsThenFeaturesSequential()
        //getFeaturesSequential()
    }

    fun performConcurrentNetworkRequests() {
        //getVersionsThenFeaturesConcurrent()
        getFeaturesConcurrent()
    }

    private fun getVersionsThenFeaturesSequential() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val features = mockApi.getRecentAndroidVersions()
                    // Map to result List<AndroidVersionFeatures> from each versions
                    .map { version ->
                        mockApi.getAndroidVersionFeatures(version.apiLevel)
                    }
                _uiState.value = UiState.Success(features)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed!")
            }

        }
    }

    private fun getFeaturesSequential() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                // Run sequentially. NOTE: code inside launch are executed sequentially
                // unless a new coroutines are started.
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val pieFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features =  mockApi.getAndroidVersionFeatures(29)

                _uiState.value = UiState.Success(listOf(oreoFeatures, pieFeatures, android10Features))
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed!")
            }

        }
    }

    private fun getVersionsThenFeaturesConcurrent() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val features = mockApi.getRecentAndroidVersions()
                    .map { version ->
                        // async returns the Deferred object
                        // Then map to List<Deferred<AndroidVersionFeatures>> from each version
                        async {
                            mockApi.getAndroidVersionFeatures(version.apiLevel)
                        }
                    }.awaitAll() // suspend, await for the completion of all List<Deferred> objects
                _uiState.value = UiState.Success(features)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }

    private fun getFeaturesConcurrent() {
        _uiState.value = UiState.Loading

        // Multiple async calls starts concurrently
        val oreoFeaturesDef = viewModelScope.async { mockApi.getAndroidVersionFeatures(27) }
        val pieFeaturesDef = viewModelScope.async { mockApi.getAndroidVersionFeatures(28) }
        val android10FeaturesDef = viewModelScope.async { mockApi.getAndroidVersionFeatures(29) }

        /**
         * When an exception occurs in a coroutine started with async, the exception
         * is not thrown directly instead async expects a call to await at some point
         * in the future, so the exception is on hold.
         * Therefore, try-catch block can be only applied around await() call
         */
        viewModelScope.launch {
            try {
                val oreoFeatures = oreoFeaturesDef.await()             // first suspension point
                val pieFeatures = pieFeaturesDef.await()               // second
                val android10Features = android10FeaturesDef.await()   // third
                _uiState.value = UiState.Success(listOf(oreoFeatures, pieFeatures, android10Features))

                //Alternatively
                //val featuresList = awaitAll(oreoFeaturesDef, pieFeaturesDef, android10FeaturesDef)
                //_uiState.value = UiState.Success(featuresList)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }
}