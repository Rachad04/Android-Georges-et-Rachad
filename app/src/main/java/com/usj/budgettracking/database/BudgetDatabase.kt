package com.usj.budgettracking.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.usj.budgettracking.data.BudgetCategory
import com.usj.budgettracking.data.BudgetCategoryDao

@Database(entities = [BudgetCategory::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetCategoryDao(): BudgetCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getDatabase(context: Context): BudgetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budget_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
