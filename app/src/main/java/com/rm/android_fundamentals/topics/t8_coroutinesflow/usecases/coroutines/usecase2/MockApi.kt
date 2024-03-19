package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase2

import com.google.gson.Gson
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.createMockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.topics.t8_coroutinesflow.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            1500
        )
        .mock(
            "http://localhost/android-version-features/29",
            { Gson().toJson(mockVersionFeaturesAndroid10) },
            200,
            1500
        )
)