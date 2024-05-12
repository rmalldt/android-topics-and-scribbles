package com.rm.android_fundamentals.topics.t6_services.bound.apicall

import com.rm.android_fundamentals.mocknetwork.mockflow.Stock
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

object FetchEventBus {
    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events = _events.asSharedFlow()

    suspend fun publishEvent(event: Event) {
        _events.emit(event)
    }
}

interface Event

data class StockEvent(val data: List<Stock>) : Event
