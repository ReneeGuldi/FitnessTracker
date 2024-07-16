package com.example.fitnesstracker.ui.views

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstracker.R
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddEditWorkoutActivity : ComponentActivity() {
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddEditWorkoutScreen(mainViewModel, workoutViewModel)
        }
    }
}

@Composable
fun AddEditWorkoutScreen(viewModel: MainViewModel, workoutViewModel: WorkoutViewModel) {
    var date by remember { mutableStateOf(Calendar.getInstance().time) }
    var workoutType by remember { mutableStateOf("") }
    var workoutDuration by remember { mutableStateOf("") }
    var caloriesBurned by remember { mutableStateOf("") }
    var workoutNotes by remember { mutableStateOf("") }

    val workoutTypes = stringArrayResource(R.array.workout_types)

    Column {
        FitnessAppBar(
            title = "Add Workout",
            onBackClick = { viewModel.navigateTo(AppUiState.MAIN_SCREEN) }
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
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            Column {
                OutlinedTextField(
                    value = workoutNotes,
                    onValueChange = { workoutNotes = it },
                    label = { Text(text = "Workout Notes" ) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Button(
                    onClick = {
                        val workout = Workout(
                            id = 0,
                            date = date,
                            workoutType = workoutType,
                            duration = workoutDuration.toIntOrNull() ?: 0,
                            caloriesBurned = caloriesBurned.toIntOrNull() ?: 0,
                            notes = if (workoutNotes.isNotBlank()) workoutNotes else null
                        )
                        workoutViewModel.insert(workout)
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

@Composable
fun WorkoutTypeDropdown(
    workoutTypes: Array<String>,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Workout Type") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(onClick = { expanded = true })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            workoutTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }

        }
    }
}

@Composable
fun DateInput(
    selectedDate: Date,
    onDateSelected: (Date) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { time = selectedDate }
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    OutlinedTextField(
        value = dateFormatter.format(selectedDate),
        onValueChange = {},
        readOnly = true,
        label = { Text(text = "Date") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        onDateSelected(calendar.time)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    )
}

