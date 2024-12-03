package com.usj.midtermrachadsouaiby.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Spending")
data class Spending(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val amount: Double,
    val date: String
)
