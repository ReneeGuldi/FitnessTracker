package com.example.fitnesstracker.app

import android.app.Application
import com.example.fitnesstracker.data.AppDatabase
import com.example.fitnesstracker.data.WeightRepository
import com.example.fitnesstracker.data.WorkoutRepository
import com.example.fitnesstracker.util.PreferencesManager

class FitnessApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val preferencesManager: PreferencesManager by lazy { PreferencesManager(this)}
    val workoutRepository: WorkoutRepository by lazy {
        WorkoutRepository(database.workoutDao())
    }

    val weightRepository: WeightRepository by lazy {
        WeightRepository(database.weightEntryDao())
    }}



