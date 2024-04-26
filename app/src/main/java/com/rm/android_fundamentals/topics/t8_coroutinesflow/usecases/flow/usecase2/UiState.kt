package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.usecase2

import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.mock.Stock
import kotlinx.coroutines.flow.Flow

sealed class UiState {
    data object Loading : UiState()
    data class Success(val stockList: List<Stock>) : UiState()
    data class Error(val message: String) : UiState()
}