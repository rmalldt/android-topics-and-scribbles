package com.rm.android_fundamentals.topics.t6_services.bound.apicall

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.rm.android_fundamentals.mocknetwork.mockflow.Stock
import com.rm.android_fundamentals.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class FetchService : Service() {

    private val fetchServiceBinder = FetchServiceBinder()

    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.IO)

    // Use hilt @Inject
    private val fetchRepository = FetchRepository(getApi(this))

    override fun onCreate() {
        fetchData()
    }

    private fun fetchData() {
        scope.launch {
            while (true) {
                val result = fetchRepository.fetchData()
                FetchEventBus.publishEvent(StockEvent(result))
                Timber.d("Fetched data: ${result[0]}")
                delay(3000)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return fetchServiceBinder
    }

    inner class FetchServiceBinder : Binder() {
        fun getFetchService() = this@FetchService
    }

    override fun onDestroy() {
        toast("Service done")
    }
}