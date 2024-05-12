package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase1

import com.rm.android_fundamentals.mocknetwork.mock.AndroidVersion

sealed class UiState {
    data object Loading : UiState()
    data class Success(val recentVersion: List<AndroidVersion>) : UiState()
    data class Error(val message: String) : UiState()
}