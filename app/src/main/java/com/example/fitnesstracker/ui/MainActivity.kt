package com.example.fitnesstracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.fitnesstracker.app.FitnessApp
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.ui.views.MainScreen
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModelFactory
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModelFactory

class MainActivity : ComponentActivity() {
    // initialize workoutViewModel
    private val workoutViewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory((application as FitnessApp).repository)
    }

    // initialize mainViewModel
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var preferencesManager: PreferencesManager

    // initialize preferencesViewModel with the preferncesManager
    private val preferencesViewModel by viewModels<PreferencesViewModel> {
        PreferencesViewModelFactory(preferencesManager)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize PreferencesManager in onCreate func
        preferencesManager = PreferencesManager(applicationContext)
        setContent {
            FitnessTrackerTheme {
                MainScreen(
                    mainViewModel = mainViewModel,
                    workoutViewModel = workoutViewModel,
                    preferencesViewModel = preferencesViewModel)
            }
        }
    }

}