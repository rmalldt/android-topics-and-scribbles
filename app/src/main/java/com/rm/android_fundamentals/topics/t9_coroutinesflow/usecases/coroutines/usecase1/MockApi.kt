package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase1

import com.google.gson.Gson
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.createMockApi
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t9_coroutinesflow.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            path = "http://localhost/recent-android-versions",
            body = { Gson().toJson(mockAndroidVersions) },
            status = 200,
            delayInMs = 1500
        )
)