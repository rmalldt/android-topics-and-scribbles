package com.rm.android_fundamentals.utils

import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.AndroidVersion
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.VersionFeatures
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeErrorApi : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw HttpException(
            Response.error<List<AndroidVersion>>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
        )
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        TODO("Not yet implemented")
    }
}