package com.example.fitnesstracker.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()
    var recentWorkouts: Flow<List<Workout>> = workoutDao.getRecentWorkouts()
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }

    suspend fun update(workout: Workout) {
        workoutDao.update(workout)
    }

    suspend fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }
}