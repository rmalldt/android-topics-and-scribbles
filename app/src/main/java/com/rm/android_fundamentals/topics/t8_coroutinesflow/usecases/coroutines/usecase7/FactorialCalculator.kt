package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase7

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.math.BigInteger

class FactorialCalculator(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun calculateFactorialOf(
        num: Int,
        noOfCoroutine: Int
    ): BigInteger {
        return withContext(defaultDispatcher) {
            val subRangeList = createSubRangeList(num, noOfCoroutine)
            subRangeList.map { subRange ->
                // Run in parallel
                async {
                    calculateFactorialOfSubRange(subRange)
                }
            }.awaitAll() // suspend, await for the completion
                // Multiply the returned factorials of sub-ranges
                .fold(BigInteger.ONE) { acc, element ->
                    acc.multiply(element)
                }
        }
    }

    private suspend fun calculateFactorialOfSubRange(subRange: SubRange): BigInteger {
        return withContext(defaultDispatcher) {
            var factorial = BigInteger.ONE
            for (i in subRange.start..subRange.end) {
                yield()
                factorial = factorial.multiply(i.toBigInteger())
            }
            factorial
        }
    }

    private fun createSubRangeList(
        factorialOf: Int,
        numberOfRange: Int
    ): List<SubRange> {
        val quotient = factorialOf.div(numberOfRange)
        val rangeList = mutableListOf<SubRange>()

        var currentIndex = 1
        repeat(numberOfRange - 1) {
            rangeList.add(
                SubRange(currentIndex, + (quotient - 1))
            )
            currentIndex += quotient
        }
        rangeList.add(SubRange(currentIndex, factorialOf))
        return rangeList
    }
}

data class SubRange(val start: Int, val end: Int)
