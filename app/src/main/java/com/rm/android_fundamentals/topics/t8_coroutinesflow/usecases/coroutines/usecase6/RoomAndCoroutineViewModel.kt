package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomAndCoroutineViewModel(
    private val repository: RoomAndCoroutineRepository
) : BaseViewModel<UiState>() {

    fun loadData() {
        _uiState.value = UiState.Loading.LoadFromDb

        viewModelScope.launch {
            // First, try to get local data from database
            val localVersions = repository.loadLocalAndroidVersionsData()
            if (localVersions.isNotEmpty()) {
                _uiState.value = UiState.Success(DataSource.DATABASE, localVersions)
            } else {
                _uiState.value = UiState.Error(DataSource.DATABASE, "Database empty!")
            }

            // Second, make network request to fetch latest data and update the database
            _uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val versions = repository.fetchAndStoreAndroidVersionData()
                _uiState.value = UiState.Success(DataSource.NETWORK, versions)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = UiState.Error(DataSource.NETWORK, "Network Request failed!")
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            repository.clearDatabase()
        }
    }
}