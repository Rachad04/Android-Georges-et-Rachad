package com.usj.budgettracking.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Adding an ID for Room to use as a primary key
    val name: String,
    val limit: Double,
    val currentSpending: Double
) {
    // Computed property for remaining budget
    val remainingBudget: Double
        get() = limit - currentSpending
}
