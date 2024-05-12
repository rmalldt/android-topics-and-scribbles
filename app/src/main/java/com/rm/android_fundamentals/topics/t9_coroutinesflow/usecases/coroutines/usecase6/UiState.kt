package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6

import com.rm.android_fundamentals.mocknetwork.mock.AndroidVersion

sealed class UiState{

    sealed class Loading : UiState() {
        data object LoadFromDb : Loading()
        data object LoadFromNetwork : Loading()
    }

    data class Success(val dataSource: DataSource, val recentVersions: List<AndroidVersion>) : UiState()

    data class Error(val dataSource: DataSource, val message: String) : UiState()
}

enum class DataSource(val dataSource: String) {
    DATABASE("Database"),
    NETWORK("Network")
}