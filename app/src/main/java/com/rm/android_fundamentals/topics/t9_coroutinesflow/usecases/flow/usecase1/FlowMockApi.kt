package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase1

import android.content.Context
import com.google.gson.Gson
import com.rm.android_fundamentals.mocknetwork.mockflow.createFlowMockApi
import com.rm.android_fundamentals.mocknetwork.mockflow.mockCurrentStockPrices
import com.rm.android_fundamentals.mocknetwork.utils.MockNetworkInterceptor

fun flowMockApi(context: Context) =
    createFlowMockApi(
        MockNetworkInterceptor()
            . mock(
                path = "/current-stock-prices",
                body = { Gson().toJson(mockCurrentStockPrices(context)) },
                status = 200,
                delayInMs = 1500,
            )
    )


fun flowMockApiWithError(context: Context) =
    createFlowMockApi(
        MockNetworkInterceptor()
            . mock(
                path = "/current-stock-prices",
                body = { Gson().toJson(mockCurrentStockPrices(context)) },
                status = 200,
                delayInMs = 1500,
                errorFrequencyInPercent = 50
            )
    )