package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2.callbacks

import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.VersionFeatures

sealed class UiState {
    data object Loading : UiState()
    data class Success(val versionFeatures: VersionFeatures) : UiState()
    data class Error(val message: String) : UiState()
}
