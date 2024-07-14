package com.example.fitnesstracker.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.Locale
@Composable
fun WorkoutHistoryScreen(mainViewModel: MainViewModel, workoutViewModel: WorkoutViewModel = viewModel()) {
    val workouts by workoutViewModel.allWorkouts!!.observeAsState(initial = emptyList())

    Column {
        FitnessAppBar(
            title = "View Workouts",
            onBackClick = {
                mainViewModel.navigateTo(AppUiState.MAIN_SCREEN)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Workout History", style = MaterialTheme.typography.headlineMedium)
            LazyColumn {
                items(workouts) { workout ->
                    WorkoutRow(workout)
                }
            }
        }
    }
}

@Composable
fun WorkoutRow(workout: Workout) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${dateFormat.format(workout.date)}")
            Text(text = "Type: ${workout.workoutType}")
            Text(text = "Duration: ${workout.duration} minutes")
            Text(text = "Calories Burned: ${workout.caloriesBurned}")
            workout.notes?.let { Text(text = "Notes: $it") }
        }
    }
}
