package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase3

import com.google.gson.Gson
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.createMockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesOreo
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesPie
import com.rm.android_fundamentals.topics.t8_coroutinesflow.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            path = "http://localhost/recent-android-versions",
            body = { Gson().toJson(mockAndroidVersions) },
            status = 200,
            delayInMs = 500
        )
        .mock(
            path = "http://localhost/android-version-features/27",
            body = { Gson().toJson(mockVersionFeaturesOreo) },
            status = 500,
            delayInMs = 500
        )
        .mock(
            path = "http://localhost/android-version-features/28",
            body = { Gson().toJson(mockVersionFeaturesPie) },
            status = 200,
            delayInMs = 500
        )
        .mock(
            path = "http://localhost/android-version-features/29",
            body = { Gson().toJson(mockVersionFeaturesAndroid10) },
            status = 200,
            delayInMs = 500
        )
)