package com.example.fitnesstracker.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.R
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel

@Composable
fun EditWorkoutScreen(
    viewModel: MainViewModel,
    workoutViewModel: WorkoutViewModel,
    workout: Workout, // Existing workout object to edit
    preferenceManager: PreferencesManager
) {
    var date by remember { mutableStateOf(workout.date) }
    var workoutType by remember { mutableStateOf(workout.workoutType) }
    var workoutDuration by remember { mutableStateOf(workout.duration.toString()) }
    var caloriesBurned by remember { mutableStateOf(workout.caloriesBurned.toString()) }
    var workoutNotes by remember { mutableStateOf(workout.notes ?: "") }
    var workoutDistance by remember { mutableStateOf(workout.distance?.toString() ?: "") }

    val workoutTypes = stringArrayResource(R.array.workout_types)
    val isKilometers = preferenceManager.isKilometers
    val distanceLabel = if (isKilometers) "Distance (km)" else "Distance (miles)"

    Surface(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FitnessAppBar(
                title = "Edit Workout",
                onBackClick = { viewModel.navigateTo(AppUiState.MAIN_SCREEN) },
                onHelpClick = { viewModel.navigateTo(AppUiState.HELP) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    DateInput(
                        selectedDate = date,
                        onDateSelected = { selectedDate ->
                            date = selectedDate
                        }
                    )

                    WorkoutTypeDropdown(
                        workoutTypes = workoutTypes,
                        selectedType = workoutType,
                        onTypeSelected = { selectedType ->
                            workoutType = selectedType
                        }
                    )

                    OutlinedTextField(
                        value = workoutDuration,
                        onValueChange = { workoutDuration = it },
                        label = { Text(text = "Workout Duration (minutes)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = workoutDistance,
                        onValueChange = { workoutDistance = it },
                        label = { Text(distanceLabel) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = caloriesBurned,
                        onValueChange = { caloriesBurned = it },
                        label = { Text(text = "Calories Burned") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
                Column {
                    OutlinedTextField(
                        value = workoutNotes,
                        onValueChange = { workoutNotes = it },
                        label = { Text(text = "Workout Notes") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    Button(
                        onClick = {
                            val updatedWorkout = Workout(
                                id = workout.id, // Ensure to pass the existing workout ID
                                date = date,
                                workoutType = workoutType,
                                duration = workoutDuration.toIntOrNull() ?: 0,
                                caloriesBurned = caloriesBurned.toIntOrNull() ?: 0,
                                notes = workoutNotes.ifBlank { null },
                                distance = workoutDistance.toFloatOrNull()
                            )
                            workoutViewModel.update(updatedWorkout)
                            viewModel.navigateTo(AppUiState.MAIN_SCREEN)
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}
