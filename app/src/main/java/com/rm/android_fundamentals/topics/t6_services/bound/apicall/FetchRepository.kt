package com.rm.android_fundamentals.topics.t6_services.bound.apicall

import com.rm.android_fundamentals.mocknetwork.mockflow.FlowMockApi
import com.rm.android_fundamentals.mocknetwork.mockflow.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchRepository(val mockApi: FlowMockApi) {

    suspend fun fetchData(): List<Stock> = withContext(Dispatchers.IO) {
        mockApi.getCurrentStockPrices()
    }
}