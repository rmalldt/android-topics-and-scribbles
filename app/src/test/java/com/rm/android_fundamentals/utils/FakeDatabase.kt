package com.rm.android_fundamentals.utils

import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersionAndroid10
import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersionOreo
import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersionPie
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6.AndroidVersionDao
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6.AndroidVersionEntity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6.mapToEntity

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