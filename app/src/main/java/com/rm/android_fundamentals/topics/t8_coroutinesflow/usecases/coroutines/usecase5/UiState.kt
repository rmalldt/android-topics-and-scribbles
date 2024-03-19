package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase5

import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.VersionFeatures

sealed class UiState {
    data object Loading : UiState()
    data class Success(val versionFeaturesList: List<VersionFeatures>) : UiState()
    data class Error(val message: String) : UiState()
}
