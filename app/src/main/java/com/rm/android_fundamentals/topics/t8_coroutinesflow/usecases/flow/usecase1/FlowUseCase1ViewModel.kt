package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class FlowUseCase1ViewModel(
    dataSource: StockPriceDataSource,
    defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<UiState>() {

    /*
        Exercise: Flow Exception Handling

        Tasks:
        - Adjust code in StockPriceDataSource and FlowUseCase3ViewModel

        Exception Handling Goals:
        - for HttpExceptions in the datasource
            - re-collect from the flow
            - delay for 5 seconds before re-collecting the flow
        - for all other Exceptions within the whole flow pipeline
            - show toast with error message by emitting UiState.Error
     */
    var currentStockPriceAsLiveData: LiveData<UiState> = dataSource
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
        .asLiveData(defaultDispatcher)
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
Flow exercise 1 Goals
    1) only update stock list when Alphabet(Google) (stock.name ="Alphabet (Google)") stock price is > 2300$
    2) only show stocks of "United States" (stock.country == "United States")
    3) show the correct rank (stock.rank) within "United States", not the world wide rank
    4) filter out Apple  (stock.name ="Apple") and Microsoft (stock.name ="Microsoft"), so that Google is number one
    5) only show company if it is one of the biggest 10 companies of the "United States" (stock.rank <= 10)
    6) stop flow collection after 10 emissions from the dataSource
    7) log out the number of the current emission so that we can check if flow collection stops after exactly 10 emissions
    8) Perform all flow processing on a background thread
 */
/*
    var currentStockPriceAsLiveData: LiveData<UiState> = dataSource
        .latestStockList
        .withIndex()
        .onEach { indexedValue ->
            Timber.d("Processing emission ${indexedValue.index + 1}")
        }
        .map { indexedValue ->
            indexedValue.value  // unwrap IndexedValue
        }
        .take(10)
        .filter { stockList ->
            val googlePrice = stockList.find { stock ->
                stock.name == "Alphabet (Google)"
            }?.currentPrice ?: return@filter false

            googlePrice > 2300
        }
        .map { stockList ->
            stockList.filter { stock ->
                stock.country == "United States" && stock.name != "Apple" && stock.name != "Microsoft"
            }.mapIndexed { index, stock ->
                stock.copy(rank = index + 1) // adjust rank
            }.filter { stock ->
                stock.rank <= 10
            }
        }
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .asLiveData(defaultDispatcher
*/

/*
// Using launchIn
fun collectStocksList() {
    val job: Job = dataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .onEach {
            currentStockPriceAsLiveData.value = it
        }
        .onCompletion {
            Timber.tag("Flow").d("Flow has completed.")
        }
        .launchIn(viewModelScope) // launch in the viewModelScope
}*/
