package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase2.callbacks

import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private var getAndroidVersionsCall: Call<List<AndroidVersion>>? = null
    private var getVersionFeaturesCall: Call<VersionFeatures>? = null

    fun performSequentialNetworkRequest() {
        _uiState.value = UiState.Loading

        // Callback 1
        getAndroidVersionsCall = mockApi.getRecentAndroidVersions() // Call object
        getAndroidVersionsCall!!.enqueue(object : Callback<List<AndroidVersion>> { // launch network call
            override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
                _uiState.value = UiState.Error("Network Request failed!")
            }

            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if (response.isSuccessful) {
                    val mostRecentAndroidVersion = response.body()!!.last()

                    // Callback 2
                    getVersionFeaturesCall = mockApi.getRecentAndroidVersionFeatures(mostRecentAndroidVersion.apiLevel) // Call object
                    getVersionFeaturesCall!!.enqueue(object : Callback<VersionFeatures> { // launch network call
                        override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                            _uiState.value = UiState.Error("Network Request failed!")
                        }

                        override fun onResponse(
                            call: Call<VersionFeatures>,
                            response: Response<VersionFeatures>
                        ) {
                            if (response.isSuccessful) {
                                val versionFeatures = response.body()!!
                                _uiState.value = UiState.Success(versionFeatures)
                            } else {
                                _uiState.value = UiState.Error("Network Request failed!")
                            }
                        }
                    })
                } else {
                    _uiState.value = UiState.Error("Network Request failed!")
                }
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getAndroidVersionsCall?.cancel()
        getVersionFeaturesCall?.cancel()
    }
}