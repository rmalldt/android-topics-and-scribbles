package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase1

import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.AndroidVersion

sealed class UiState {
    data object Loading : UiState()
    data class Success(val recentVersion: List<AndroidVersion>) : UiState()
    data class Error(val message: String) : UiState()
}