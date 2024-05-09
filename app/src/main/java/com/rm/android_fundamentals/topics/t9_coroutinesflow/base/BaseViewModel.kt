package com.rm.android_fundamentals.topics.t9_coroutinesflow.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T> : ViewModel() {

    internal val _uiState: MutableLiveData<T> = MutableLiveData()
    val uiState: LiveData<T> get() = _uiState
}