package com.usj.budgettracking

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.usj.budgettracking.data.BudgetCategory
import com.usj.budgettracking.data.BudgetCategoryDao
import com.usj.budgettracking.database.BudgetDatabase
import com.usj.budgettracking.Repository.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val budgetCategoryDao: BudgetCategoryDao = BudgetDatabase.getDatabase(application).budgetCategoryDao()
    private val repository: BudgetRepository = BudgetRepository(budgetCategoryDao)

    val allBudgets: LiveData<List<BudgetCategory>> = repository.allBudgets

    fun insert(budgetCategory: BudgetCategory) {
        Log.d("BudgetTracking", "Inserting category: ${budgetCategory.name}")
        viewModelScope.launch {
            repository.insert(budgetCategory)
        }
    }

    fun delete(budgetCategory: BudgetCategory) {
        viewModelScope.launch {
            repository.delete(budgetCategory)
        }
    }

    fun update(budgetCategory: BudgetCategory) {
        viewModelScope.launch {
            repository.update(budgetCategory)
        }
    }
}
