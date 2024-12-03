package com.usj.budgettracking.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetCategoryDao {

    @Insert
    suspend fun insert(budgetCategory: BudgetCategory)

    @Query("SELECT * FROM budgets ORDER BY name DESC")
    fun getAllBudgets(): LiveData<List<BudgetCategory>>

    @Delete
    suspend fun delete(budgetCategory: BudgetCategory)

    @Update
    suspend fun update(budgetCategory: BudgetCategory)
}
