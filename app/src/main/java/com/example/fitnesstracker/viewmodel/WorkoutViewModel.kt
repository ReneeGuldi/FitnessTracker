package com.example.fitnesstracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.data.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {

    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts

    fun insert(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }

    fun update(workout: Workout) = viewModelScope.launch {
        repository.update(workout)
    }

    fun delete(workout: Workout) = viewModelScope.launch {
        repository.delete(workout)
    }
}


class WorkoutViewModelFactory(private val repository: WorkoutRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}