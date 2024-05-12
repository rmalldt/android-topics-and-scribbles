package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase2

import com.rm.android_fundamentals.mocknetwork.mockflow.Stock

sealed class UiState {
    data object Loading : UiState()
    data class Success(val stockList: List<Stock>) : UiState()
    data class Error(val message: String) : UiState()
}