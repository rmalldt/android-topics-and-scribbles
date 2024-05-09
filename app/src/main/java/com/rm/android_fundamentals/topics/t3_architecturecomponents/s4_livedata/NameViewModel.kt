package com.rm.android_fundamentals.topics.t3_architecturecomponents.s4_livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NameViewModel(
    private val nameRepository: NameRepository
) : ViewModel() {

    // Create a LiveData with String
    val currentName: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun updateName() {
        currentName.value = nameRepository.getName()
    }

    class Factory(
        private val nameRepository: NameRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NameViewModel(nameRepository) as T
        }
    }
}

