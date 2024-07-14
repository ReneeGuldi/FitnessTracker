package com.example.fitnesstracker.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager

class WorkoutRepository(private val workoutDao: WorkoutDao, private val context: Context) {

    val recentWorkouts: LiveData<List<Workout>> = workoutDao.getRecentWorkouts()

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

    // get all workouts in database
    fun getAllWorkOuts(): LiveData<List<Workout>> {
        return workoutDao.getAllWorkOuts()
    }
    // update top workout types to save
    private fun updateTopWorkoutTypes(workouts: List<Workout>) {
        val workoutTypes = workouts.groupBy { it.workoutType }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
            .take(3)
            .map { it.first }
            .toSet()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putStringSet("top_workout_types", workoutTypes)
            apply()
        }
    }
}