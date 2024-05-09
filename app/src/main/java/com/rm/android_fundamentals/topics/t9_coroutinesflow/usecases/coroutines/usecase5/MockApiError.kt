package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase5

import com.google.gson.Gson
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.createMockApi
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockVersionFeaturesOreo
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockVersionFeaturesPie
import com.rm.android_fundamentals.topics.t9_coroutinesflow.utils.MockNetworkInterceptor

fun mockApiError() = createMockApi(
    MockNetworkInterceptor()
        // Versions: Timeout on first request
        .mock(
            path = "http://localhost/recent-android-versions",
            body = { Gson().toJson(mockAndroidVersions) },
            status = 200,
            delayInMs = 1500,
            persist = false
        )
        // Versions: Network error on second request
        .mock(
            path = "http://localhost/recent-android-versions",
            body = { "Something went wrong on servers side" },
            status =500,
            delayInMs = 100,
            persist = false
        )
        // Versions: Successful on third request within timeout
        .mock(
            path = "http://localhost/recent-android-versions",
            body = { Gson().toJson(mockAndroidVersions) },
            status = 200,
            delayInMs = 300
        )
        // Oreo features: Timeout on first request for oreo features
        .mock(
            "http://localhost/android-version-features/27",
            { Gson().toJson(mockVersionFeaturesOreo) },
            200,
            1050,
            persist = false
        )
        // Oreo features: Network error on second request
        .mock(
            "http://localhost/android-version-features/27",
            { "Something went wrong on servers side" },
            500,
            100,
            persist = false
        )
        // Oreo features: Server error on third request
        .mock(
            "http://localhost/android-version-features/27",
            { Gson().toJson(mockVersionFeaturesOreo) },
            MockNetworkInterceptor.INTERNAL_SERVER_ERROR,
            100
        )
        // Pie features: Timeout on first request for oreo features
        .mock(
            "http://localhost/android-version-features/28",
            { Gson().toJson(mockVersionFeaturesPie) },
            200,
            1050,
            persist = false
        )
        // Pie features: Network error on second request
        .mock(
            "http://localhost/android-version-features/28",
            { "Something went wrong on servers side" },
            500,
            100,
            persist = false
        )
        // Pie features: Successful on third request within timeout
        .mock(
            "http://localhost/android-version-features/28",
            { Gson().toJson(mockVersionFeaturesPie) },
            200,
            100
        )
        // Android 10 features: Successful on first request within timeout
        .mock(
            "http://localhost/android-version-features/29",
            { Gson().toJson(mockVersionFeaturesAndroid10) },
            200,
            100
        )
)