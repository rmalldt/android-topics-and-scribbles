package com.rm.android_fundamentals.utils

import com.rm.android_fundamentals.mocknetwork.mock.AndroidVersion
import com.rm.android_fundamentals.mocknetwork.mock.MockApi
import com.rm.android_fundamentals.mocknetwork.mock.VersionFeatures
import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersions
import com.rm.android_fundamentals.mocknetwork.mock.mockVersionFeaturesAndroid10

class FakeSuccessApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        return mockVersionFeaturesAndroid10
    }
}
