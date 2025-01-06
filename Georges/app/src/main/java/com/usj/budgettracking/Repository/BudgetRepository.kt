package com.usj.budgettracking.Repository

import androidx.lifecycle.LiveData
import com.usj.budgettracking.data.BudgetCategory
import com.usj.budgettracking.data.BudgetCategoryDao

class BudgetRepository(private val budgetCategoryDao: BudgetCategoryDao) {

    val allBudgets: LiveData<List<BudgetCategory>> = budgetCategoryDao.getAllBudgets()

    suspend fun insert(budgetCategory: BudgetCategory) {
        budgetCategoryDao.insert(budgetCategory)
    }

    suspend fun delete(budgetCategory: BudgetCategory) {
        budgetCategoryDao.delete(budgetCategory)
    }

    suspend fun update(budgetCategory: BudgetCategory) {
        budgetCategoryDao.update(budgetCategory)
    }
}
