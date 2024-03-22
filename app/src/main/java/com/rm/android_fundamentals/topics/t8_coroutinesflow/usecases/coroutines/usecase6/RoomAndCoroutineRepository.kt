package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6

import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomAndCoroutineRepository(
    private val coroutineScope: CoroutineScope,
    private val dao: AndroidVersionDao,
    private val mockApi: MockApi = mockApi()
) {

    // Executes in the viewModelScope started from viewModelScope
    suspend fun loadLocalAndroidVersionsData(): List<AndroidVersion> =
        dao.getAndroidVersions().mapToUiModelList()

    // Executes in the applicationScope although started from viewModelScope
    suspend fun fetchAndStoreAndroidVersionData(): List<AndroidVersion> = coroutineScope.async {
        val versions = mockApi.getRecentAndroidVersions()
        Timber.d("Android versions fetched")
        for (version in versions) {
            Timber.d("Inserting $version")
            dao.insert(version.mapToEntity())
        }
        versions
    }.await()

    // Executes in the applicationScope although started from viewModelScope
    fun clearDatabase() {
        coroutineScope.launch {
            dao.clear()
        }
    }
}