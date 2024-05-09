package com.rm.android_fundamentals

import android.app.Application
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6.AndroidVersionDatabase
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6.RoomAndCoroutineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class AndroidFundamentalsApp : Application() {

    /**
     * Application scoped coroutine with SupervisorJob to be used
     * by any classes that needs to outlive the lifecycle scope of
     * specific entity such as ViewModel, Activity and Fragments.
     */
    private val applicationScope = CoroutineScope(SupervisorJob())

    val roomAndCoroutineRepository by lazy {
        val dao = AndroidVersionDatabase.getInstance(applicationContext).androidVersionDao()
        RoomAndCoroutineRepository(
            applicationScope,
            dao
        )
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        /**
         * Enable Debugging for Kotlin Coroutines in debug builds
         * Prints Coroutine name when logging Thread.currentThread().name
         */
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
    }
}