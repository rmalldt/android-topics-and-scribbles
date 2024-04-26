package com.rm.android_fundamentals.utils

import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersionAndroid10
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersionOreo
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersionPie
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6.AndroidVersionDao
import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6.AndroidVersionEntity
import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6.mapToEntity

class FakeDatabase : AndroidVersionDao {

    var insertedIntoDb = false

    override suspend fun getAndroidVersions(): List<AndroidVersionEntity> {
        return listOf(
            mockAndroidVersionOreo.mapToEntity(),
            mockAndroidVersionPie.mapToEntity(),
            mockAndroidVersionAndroid10.mapToEntity()
        )
    }

    override suspend fun insert(androidVersionEntity: AndroidVersionEntity) {
        insertedIntoDb = true
    }

    override suspend fun clear() {}
}