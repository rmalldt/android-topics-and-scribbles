package com.rm.android_fundamentals.topics.t3_architecturecomponents.s1_lifecycle.withoutlifecyclecomponent

import android.content.Context

internal class MyListener(
    private val context: Context,
    private val callback: (String) -> Unit
) {

    private val processName = context.applicationInfo.processName

    fun start() = callback.invoke("Process name: $processName, invoked on start")

    fun stop() = callback.invoke("Process name: $processName, invoked on stop")
}