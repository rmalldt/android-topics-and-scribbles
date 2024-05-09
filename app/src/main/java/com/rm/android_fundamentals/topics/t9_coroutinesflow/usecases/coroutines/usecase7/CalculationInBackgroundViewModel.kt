package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.BaseViewModel
import com.rm.android_fundamentals.utils.EMPTY_STRING
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import java.util.concurrent.CancellationException
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel(
    private val factorialCalculator: FactorialCalculator = FactorialCalculator(),
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseViewModel<UiState>() {

    private var calculationJob: Job? = null

    fun performCalculation(num: Int, noOfCoroutines: Int) {
        _uiState.value = UiState.Loading

        calculationJob = viewModelScope.launch {
            try {
                var result = BigInteger.ZERO
                val computationDuration = measureTimeMillis {
                    result = factorialCalculator.calculateFactorialOf(num, noOfCoroutines)
                    Timber.d("Calculating factorial completed")
                }

                var resultString = EMPTY_STRING
                val stringConversionDuration = measureTimeMillis {
                    resultString = convertToString(result)
                }
                _uiState.value = UiState.Success(resultString, computationDuration, stringConversionDuration)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error while calculating result")
            }
        }

        calculationJob?.invokeOnCompletion {
            if (it is CancellationException) {
                Timber.d("Calculation was cancelled")
            }
        }
    }

    fun performCalculation(num: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                var result = BigInteger.ZERO
                val computationDuration = measureTimeMillis {
                    result = calculateFactorialOf(num)     // run on background
                }

                var resultString = EMPTY_STRING
                val stringConversionDuration = measureTimeMillis {
                    resultString = convertToString(result) // run on background
                }

                // Switch to Dispatcher.Main
                _uiState.value = UiState.Success(resultString, computationDuration, stringConversionDuration)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error while calculating result")
            }
        }
    }

    // Run on the background with withContext -> Dispatcher.DEFAULT
    private suspend fun calculateFactorialOf(num: Int): BigInteger =
        withContext(defaultDispatcher) {
            var factorial = BigInteger.ONE
            for (i in 1..num) {
                factorial = factorial.multiply(i.toBigInteger())
            }
            factorial
        }
    // Run on the background with withContext -> Dispatcher.DEFAULT
    private suspend fun convertToString(result: BigInteger) =
        withContext(defaultDispatcher + CoroutineName("String conversion")) {
            result.toString()
        }
}
