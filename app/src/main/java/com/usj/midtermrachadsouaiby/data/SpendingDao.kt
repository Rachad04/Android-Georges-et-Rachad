package com.usj.midtermrachadsouaiby.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpending(spending: Spending): Long

    @Query("SELECT * FROM Spending ORDER BY date DESC")
    fun getAllSpendings(): Flow<List<Spending>>
}
