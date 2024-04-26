package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.usecase2

import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.mock.FlowMockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.mock.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import timber.log.Timber

interface StockPriceDataSource {
    val latestStockList: Flow<List<Stock>>
}

class NetworkStockPriceDataSource(val mockApi: FlowMockApi) : StockPriceDataSource {

    override val latestStockList: Flow<List<Stock>> = flow {
            while (true) {
                Timber.tag("Flow").d("Fetching current stock prices")
                val currentStockList = mockApi.getCurrentStockPrices()
                emit(currentStockList)
                delay(5000) // fetch data every 5s
            }
        }
        .flowOn(Dispatchers.Default)
        .retryWhen { cause: Throwable, attempt: Long ->
        Timber.tag("Flow").d("Retry with $cause")
        val shouldRetry = cause is HttpException
        if (shouldRetry) {
            delay(1000 * (attempt + 1))
        }
        shouldRetry
    }
}
