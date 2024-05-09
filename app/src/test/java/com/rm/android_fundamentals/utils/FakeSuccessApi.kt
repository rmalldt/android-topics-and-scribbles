package com.rm.android_fundamentals.utils

import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.MockApi
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.VersionFeatures
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockVersionFeaturesAndroid10

class FakeSuccessApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        return mockVersionFeaturesAndroid10
    }
}
