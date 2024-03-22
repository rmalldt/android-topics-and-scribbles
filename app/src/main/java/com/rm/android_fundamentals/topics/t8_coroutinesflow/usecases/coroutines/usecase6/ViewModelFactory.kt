package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi

class ViewModelFactory(
    private val repository: RoomAndCoroutineRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RoomAndCoroutineRepository::class.java)
            .newInstance(repository)
    }
}