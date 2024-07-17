package com.example.fitnesstracker.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun WorkoutHistoryScreen(mainViewModel: MainViewModel, workoutViewModel: WorkoutViewModel) {
    // Ensure that allWorkouts is not null
    val workoutsState = workoutViewModel.allWorkouts.observeAsState(initial = emptyList())


    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            FitnessAppBar(
                title = "View Workouts",
                onBackClick = {
                    mainViewModel.navigateTo(AppUiState.MAIN_SCREEN)
                },
                onHelpClick = { mainViewModel.navigateTo(AppUiState.HELP) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Workout History", style = MaterialTheme.typography.headlineMedium)
                LazyColumn {
                    items(workoutsState.value) { workout ->
                        WorkoutRow(
                            workout,
                            onEditClick = { selectedWorkout ->
                                mainViewModel.setSelectedWorkout(selectedWorkout)
                                mainViewModel.navigateTo(AppUiState.EDIT_WORKOUT)
                            },
                            onDeleteClick = { selectedWorkout ->
                                workoutViewModel.delete(selectedWorkout)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutRow(
    workout: Workout,
    onEditClick: (Workout) -> Unit,
    onDeleteClick: (Workout) -> Unit
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Date: ${dateFormat.format(workout.date)}")
                    Text(text = "Type: ${workout.workoutType}")
                    Text(text = "Duration: ${workout.duration} minutes")
                    Text(text = "Calories Burned: ${workout.caloriesBurned}")
                    workout.notes?.let { Text(text = "Notes: $it") }
                }
                Row {
                    Button(onClick = { onEditClick(workout) }) {
                        Text(text = "Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onDeleteClick(workout) }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}



