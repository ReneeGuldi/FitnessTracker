package com.example.fitnesstracker.data

import androidx.lifecycle.LiveData

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkOuts()
    // add a new workout
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
    // update workout in database
    suspend fun update(workout: Workout) {
        workoutDao.update(workout)
    }

    // remove workout from database
    suspend fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }

}