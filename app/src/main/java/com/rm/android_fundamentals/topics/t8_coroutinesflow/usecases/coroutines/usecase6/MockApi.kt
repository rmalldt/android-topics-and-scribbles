package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6

import com.google.gson.Gson
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.createMockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t8_coroutinesflow.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            5000
        )
)