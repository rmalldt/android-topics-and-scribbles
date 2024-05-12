package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2.rx

import com.rm.android_fundamentals.mocknetwork.mock.VersionFeatures

sealed class UiState {
    data object Loading : UiState()
    data class Success(val versionFeatures: VersionFeatures) : UiState()
    data class Error(val message: String) : UiState()
}
