package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase4

import com.google.gson.Gson
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.createMockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesOreo
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesPie
import com.rm.android_fundamentals.topics.t8_coroutinesflow.utils.MockNetworkInterceptor

fun mockApiTimeOut() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            1000
        )
)

fun mockApiRetries() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { "something went wrong on server side" },
            500,
            1000,
            persist = false
        ).mock(
            "http://localhost/recent-android-versions",
            { "something went wrong on server side" },
            500,
            1000,
            persist = false
        ).mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            1000
        )
)