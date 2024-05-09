package com.rm.android_fundamentals.topics.t3_architecturecomponents.s3_lifecycle.withlifecyclecomponent

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class MyLifecycleObserver(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private val callback: (String) -> Unit
) : DefaultLifecycleObserver {

    private val processName = context.applicationInfo.processName

    private var enabled = false

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        callback.invoke("Process name: $processName, started onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        callback.invoke("Process name: $processName, started onPause")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        callback.invoke("Process name: $processName, started onDestroy")
    }

    fun enabled(): String {
        enabled = true

        return when  {
            lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED) -> "INITIALIZED"
            lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) -> "CREATED"
            lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) -> "STARTED"
            lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) -> "RESUMED"
            lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED) -> "DESTROYED"
            else -> "UNKNOWN"
        }
    }
}