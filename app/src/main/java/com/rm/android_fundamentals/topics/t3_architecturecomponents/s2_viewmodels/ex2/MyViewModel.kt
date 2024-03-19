package com.rm.android_fundamentals.topics.t3_architecturecomponents.s2_viewmodels.ex2

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


class MyViewModel(
    private val myRepository: MyRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ViewModel logic ...

    /*companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                val savedStateHandle = extras.createSavedStateHandle()

                return MyViewModel(
                    (application as MyApplication).myRepository,
                    savedStateHandle) as T
            }
        }
    }*/
}