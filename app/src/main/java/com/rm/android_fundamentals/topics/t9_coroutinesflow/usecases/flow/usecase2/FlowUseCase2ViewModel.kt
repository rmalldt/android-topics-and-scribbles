package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class FlowUseCase2ViewModel(
    dataSource: StockPriceDataSource,
    defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<UiState>() {

    var currentStockPriceAsFlow = dataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        .catch {throwable ->
            Timber.tag("Flow").d("Caught $throwable")
            emit(UiState.Error("Something went wrong"))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = UiState.Loading
        )

}

class ViewModelFactory(
    private val stockPriceDataSource: StockPriceDataSource,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            StockPriceDataSource::class.java,
            CoroutineDispatcher::class.java
        ).newInstance(stockPriceDataSource, defaultDispatcher)
    }
}

/*
// Using SharedFlow
var currentStockPriceAsFlow: Flow<UiState> = dataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        .catch {throwable ->
            Timber.tag("Flow").d("Caught $throwable")
            emit(UiState.Error("Something went wrong"))
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            replay = 1
        )
 */

/*
//Using cold flow
var currentStockPriceAsFlow: Flow<UiState> = dataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        .catch {throwable ->
            Timber.tag("Flow").d("Caught $throwable")
            emit(UiState.Error("Something went wrong"))
        }
 */
