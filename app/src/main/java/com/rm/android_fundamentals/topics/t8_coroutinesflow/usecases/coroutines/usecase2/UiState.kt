package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase2

import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.VersionFeatures

sealed class UiState {
    data object Loading : UiState()
    data class Success(val versionFeatures: VersionFeatures) : UiState()
    data class Error(val message: String) : UiState()
}