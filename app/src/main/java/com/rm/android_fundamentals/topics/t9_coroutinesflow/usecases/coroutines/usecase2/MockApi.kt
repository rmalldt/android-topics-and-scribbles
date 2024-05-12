package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2

import com.google.gson.Gson
import com.rm.android_fundamentals.mocknetwork.mock.createMockApi
import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersions
import com.rm.android_fundamentals.mocknetwork.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.mocknetwork.utils.MockNetworkInterceptor

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