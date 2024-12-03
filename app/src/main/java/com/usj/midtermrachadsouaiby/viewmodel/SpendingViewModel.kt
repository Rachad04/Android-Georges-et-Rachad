package com.usj.midtermrachadsouaiby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.usj.midtermrachadsouaiby.data.Spending
import com.usj.midtermrachadsouaiby.repository.SpendingRepository
import kotlinx.coroutines.launch

class SpendingViewModel(private val repository: SpendingRepository) : ViewModel() {
    val allSpendings = repository.allSpendings.asLiveData()

    fun addSpending(spending: Spending) {
        viewModelScope.launch {
            repository.insertSpending(spending)
        }
    }
}

class SpendingViewModelFactory(private val repository: SpendingRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpendingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpendingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
