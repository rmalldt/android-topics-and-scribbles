package com.rm.android_fundamentals.topics.t3_architecturecomponents.s5_viewmodels.ex2

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SavedStateHandleViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var count = 0
    var counter = savedStateHandle.getStateFlow(KEY, 0) // get as StateFlow

    fun incrementCounter() {
        count++
        savedStateHandle[KEY] = count
    }

    companion object {
        const val KEY = "counter"
    }
}