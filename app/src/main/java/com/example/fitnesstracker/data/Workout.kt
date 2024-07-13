package com.example.fitnesstracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val workoutType: String,
    val duration: Int,
    val caloriesBurned: Int,
    val notes: String? = null

)
