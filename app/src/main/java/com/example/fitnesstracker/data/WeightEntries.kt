package com.example.fitnesstracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weight_entries")
data class WeightEntries(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: Float,
    val date: String
)
