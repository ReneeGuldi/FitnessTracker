package com.example.fitnesstracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.app.FitnessApp
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.data.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: WorkoutRepository? = null
    var allWorkouts: LiveData<List<Workout>>? = null


    init {
        if (application is FitnessApp) {
            val workoutDao = application.database.workoutDao()
            repository = WorkoutRepository(workoutDao, application)
            allWorkouts = repository?.getAllWorkOuts()
        } else {
            allWorkouts = MutableLiveData(emptyList<Workout>())
        }
    }

    fun insert(workout: Workout) = viewModelScope.launch {
        repository?.insert(workout)
    }

    fun update(workout: Workout) = viewModelScope.launch {
        repository?.update(workout)
    }

    fun delete(workout: Workout) = viewModelScope.launch {
        repository?.delete(workout)
    }
}