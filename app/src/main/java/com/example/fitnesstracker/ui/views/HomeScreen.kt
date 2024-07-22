package com.example.fitnesstracker.ui.views

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.fitnesstracker.app.FitnessApp
import com.example.fitnesstracker.data.Workout
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel
import com.example.fitnesstracker.viewmodel.WeightViewModel
import com.example.fitnesstracker.viewmodel.WeightViewModelFactory
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    workoutViewModel: WorkoutViewModel,
    weightViewModel: WeightViewModel,
    preferencesViewModel: PreferencesViewModel
) {
    val uiState by mainViewModel.uiState.collectAsState()
    val allWorkouts by workoutViewModel.allWorkouts.observeAsState(emptyList())
    val selectedWorkout by mainViewModel.selectedWorkout.collectAsState()
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)

    when (uiState) {
        AppUiState.MAIN_SCREEN -> {
            if (allWorkouts.isEmpty()) {
                NoWorkoutsMessage(mainViewModel)
            } else {
                MainFragment(mainViewModel, workoutViewModel, weightViewModel)
            }
        }
        AppUiState.ADD_WORKOUT -> AddWorkoutScreen(mainViewModel, workoutViewModel, preferencesManager)
        AppUiState.EDIT_WORKOUT -> {
            selectedWorkout?.let {
                EditWorkoutScreen(mainViewModel, workoutViewModel, it, preferencesManager)
            }
        }
        AppUiState.VIEW_WORKOUTS -> WorkoutHistoryScreen(mainViewModel, workoutViewModel)
        AppUiState.USER_PREFERENCES -> PreferencesScreen(mainViewModel, preferencesViewModel)
        AppUiState.HELP -> HelpFragment(mainViewModel)
        AppUiState.WEIGHT_SCREEN -> WeightEntriesScreen(
            weightViewModel,
            mainViewModel,
            onAddWeightEntry = { mainViewModel.navigateTo(AppUiState.ADD_WEIGHT) },
            preferencesManager
        )
        AppUiState.ADD_WEIGHT -> AddWeightEntryScreen(weightViewModel, mainViewModel, preferencesManager)
    }
}

@Composable
fun NoWorkoutsMessage(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No workouts recorded. Please add a new workout.",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.navigateTo(AppUiState.ADD_WORKOUT) }) {
            Text(text = "Add Workout")
        }
    }
}

@Composable
fun MainFragment(
    mainViewModel: MainViewModel,
    workoutViewModel: WorkoutViewModel,
    weightViewModel: WeightViewModel
) {
    val recentWorkouts by workoutViewModel.recentWorkouts.collectAsState(emptyList())

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            FitnessAppBar(
                title = "PeakForm - Home",
                onBackClick = { mainViewModel.navigateTo(AppUiState.MAIN_SCREEN) },
                onHelpClick = { mainViewModel.navigateTo(AppUiState.HELP) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { mainViewModel.navigateTo(AppUiState.WEIGHT_SCREEN) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = "Weight Entries")
                }
                Button(
                    onClick = { mainViewModel.navigateTo(AppUiState.ADD_WORKOUT) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = "Add Workout")
                }
                Button(
                    onClick = { mainViewModel.navigateTo(AppUiState.VIEW_WORKOUTS) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = "View All Workouts")
                }
                Button(
                    onClick = { mainViewModel.navigateTo(AppUiState.USER_PREFERENCES) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = "User Prefs.")
                }
                Text(
                    text = "Recent Workouts",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
                RecentWorkoutsColumn(
                    recentWorkouts = recentWorkouts,
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
@Composable
fun FitnessAppBar(
    title: String,
    onBackClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(Icons.Outlined.Home, contentDescription = "Main Menu", tint = Color.White)
        }
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Center)
        )
        IconButton(
            onClick = onHelpClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(Icons.Outlined.Info, contentDescription = "Help", tint = Color.White)
        }
    }
}

@Composable
fun HelpFragment(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Help Screen",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Instructions
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "1. Add Workouts",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "To add a workout, go to the Add Workout screen from the main menu. Fill in the details such as date, type of workout, duration, and calories burned. Optionally, you can add notes and distance if applicable. Save the workout to track your exercise activities.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "2. Track Weight",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "To track your weight, navigate to the Weight Tracking section from the main menu. You can add your weight records with the date automatically recorded. View your weight history and monitor progress through visual graphs.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Back button
        Button(
            onClick = { viewModel.navigateTo(AppUiState.MAIN_SCREEN) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Main")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val context = LocalContext.current
    val fitnessApp = (context as Activity).application as FitnessApp

    val mainViewModel = MainViewModel().apply {
        navigateTo(AppUiState.MAIN_SCREEN)
    }


    // Initialize PreferencesManager
    val preferencesManager = PreferencesManager(context)
    val preferencesViewModel = PreferencesViewModel(preferencesManager)

    val workoutViewModel = ViewModelProvider(
        LocalViewModelStoreOwner.current!!,
        WorkoutViewModelFactory(fitnessApp.workoutRepository)
    )[WorkoutViewModel::class.java]

    val weightViewModel = ViewModelProvider(
        LocalViewModelStoreOwner.current!!,
        WeightViewModelFactory(fitnessApp.weightRepository)
    )[WeightViewModel::class.java]
    FitnessTrackerTheme {
        MainScreen(mainViewModel, workoutViewModel, weightViewModel, preferencesViewModel)
    }
}

@Composable
fun RecentWorkoutsColumn(
    recentWorkouts: List<Workout>,
    onEditClick: (Workout) -> Unit,
    onDeleteClick: (Workout) -> Unit
) {
    LazyColumn {
        items(recentWorkouts) { workout ->
            WorkoutCard(workout)
        }
    }
}

@Composable
fun WorkoutCard(
    workout: Workout
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Date: ${dateFormat.format(workout.date)}")
            Text(text = "Workout Type: ${workout.workoutType}")
            Text(text = "Duration: ${workout.duration} mins")
            Text(text = "Calories Burned: ${workout.caloriesBurned}")
            workout.notes?.let { Text(text = "Notes: $it") }
        }
    }
}
