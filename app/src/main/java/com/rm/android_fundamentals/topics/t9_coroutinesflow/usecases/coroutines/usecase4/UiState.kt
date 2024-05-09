package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase4

import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.AndroidVersion

sealed class UiState {
    data object Loading : UiState()
    data class Success(val recentVersions: List<AndroidVersion>) : UiState()
    data class Error(val message: String) : UiState()
}
