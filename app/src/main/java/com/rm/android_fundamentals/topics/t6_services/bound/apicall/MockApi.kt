package com.rm.android_fundamentals.topics.t6_services.bound.apicall

import android.content.Context
import com.google.gson.Gson
import com.rm.android_fundamentals.mocknetwork.mock.createMockApi
import com.rm.android_fundamentals.mocknetwork.mockflow.FlowMockApi
import com.rm.android_fundamentals.mocknetwork.mockflow.createFlowMockApi
import com.rm.android_fundamentals.mocknetwork.mockflow.mockCurrentStockPrices
import com.rm.android_fundamentals.mocknetwork.utils.MockNetworkInterceptor

fun getApi(context: Context): FlowMockApi {
    return createFlowMockApi(
        MockNetworkInterceptor()
            .mock(
                path = "/current-stock-prices",
                body = { Gson().toJson(mockCurrentStockPrices(context)) },
                status = 200,
                delayInMs = 1500,
            )
    )
}