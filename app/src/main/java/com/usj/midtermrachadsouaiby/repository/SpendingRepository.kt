package com.usj.midtermrachadsouaiby.repository

import com.usj.midtermrachadsouaiby.data.Spending
import com.usj.midtermrachadsouaiby.data.SpendingDao
import kotlinx.coroutines.flow.Flow

class SpendingRepository(private val spendingDao: SpendingDao) {
    val allSpendings: Flow<List<Spending>> = spendingDao.getAllSpendings()

    suspend fun insertSpending(spending: Spending) {
        spendingDao.insertSpending(spending)
    }
}
