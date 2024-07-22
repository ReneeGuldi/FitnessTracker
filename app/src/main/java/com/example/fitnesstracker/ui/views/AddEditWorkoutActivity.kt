package com.example.fitnesstracker.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel


class AddEditWorkoutActivity : ComponentActivity() {
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var preferenceManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddWorkoutScreen(mainViewModel, workoutViewModel, preferenceManager)
        }
    }
}


