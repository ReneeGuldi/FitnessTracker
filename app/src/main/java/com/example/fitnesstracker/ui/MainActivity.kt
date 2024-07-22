package com.example.fitnesstracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesstracker.app.FitnessApp
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.ui.views.MainScreen
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModelFactory
import com.example.fitnesstracker.viewmodel.WeightViewModel
import com.example.fitnesstracker.viewmodel.WeightViewModelFactory
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModelFactory

class MainActivity : ComponentActivity() {
    // Initialize workoutViewModel
    private val workoutViewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory((application as FitnessApp).workoutRepository)
    }

    // Initialize mainViewModel
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var preferencesViewModel: PreferencesViewModel
    private lateinit var weightViewModel: WeightViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize PreferencesManager in onCreate function
        preferencesManager = PreferencesManager(applicationContext)

        // Initialize preferencesViewModel with the preferencesManager
        preferencesViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory(preferencesManager)
        )[PreferencesViewModel::class.java]

        // Initialize weightViewModel
        weightViewModel = ViewModelProvider(
            this,
            WeightViewModelFactory((application as FitnessApp).weightRepository)
        )[WeightViewModel::class.java]
        setContent {
            FitnessTrackerTheme {
                MainScreen(
                    mainViewModel = mainViewModel,
                    workoutViewModel = workoutViewModel,
                    weightViewModel = weightViewModel,
                    preferencesViewModel = preferencesViewModel
                )
            }
        }
    }
}