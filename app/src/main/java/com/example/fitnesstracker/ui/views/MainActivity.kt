package com.example.fitnesstracker.ui.views

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.fitnesstracker.app.FitnessApp
import com.example.fitnesstracker.data.AppDatabase
import com.example.fitnesstracker.data.WorkoutRepository
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModelFactory

class MainActivity : ComponentActivity() {

    private val workoutViewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory((application as FitnessApp).repository)
    }
    private val mainViewModel: MainViewModel by viewModels()
    private val preferencesViewModel: PreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessTrackerTheme {
                MainScreen(mainViewModel, workoutViewModel, preferencesViewModel)
            }
        }
    }
}
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    workoutViewModel: WorkoutViewModel,
    preferencesViewModel: PreferencesViewModel
) {
    val uiState by mainViewModel.uiState.collectAsState()
    val allWorkouts by workoutViewModel.allWorkouts.observeAsState(emptyList())

    when (uiState) {
        AppUiState.MAIN_SCREEN -> {
            if (allWorkouts.isEmpty()) {
                NoWorkoutsMessage(mainViewModel)
            } else {
                MainFragment(mainViewModel, workoutViewModel)
            }
        }
        AppUiState.ADD_EDIT_WORKOUT -> AddEditWorkoutScreen(mainViewModel, workoutViewModel)
        AppUiState.VIEW_WORKOUTS -> WorkoutHistoryScreen(mainViewModel, workoutViewModel)
        AppUiState.USER_PREFERENCES -> PreferencesScreen(mainViewModel, preferencesViewModel)
        AppUiState.HELP -> HelpFragment(mainViewModel)
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
        Button(onClick = { viewModel.navigateTo(AppUiState.ADD_EDIT_WORKOUT) }) {
            Text(text = "Add Workout")
        }
    }
}


@Composable
fun MainFragment(
    mainViewModel: MainViewModel,
    workoutViewModel: WorkoutViewModel
) {
    val recentWorkouts by workoutViewModel.recentWorkouts.collectAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { mainViewModel.navigateTo(AppUiState.ADD_EDIT_WORKOUT) },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "Add Workout")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { mainViewModel.navigateTo(AppUiState.VIEW_WORKOUTS) },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "View All Workouts")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { mainViewModel.navigateTo(AppUiState.USER_PREFERENCES) },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "User Prefs.")
            }
        }
        Text(
            text = "Recent Workouts",
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.Black
        )
        Column {
            recentWorkouts.forEach { workout ->
                val formattedDate = workout.date.toString()
                Text(
                    text = "Date: $formattedDate, Workout Type: ${workout.workoutType}, Duration: ${workout.duration} mins",
                    color = Color.Gray)
            }
        }
    }
}
@Composable
fun FitnessAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun HelpFragment(viewModel: MainViewModel) {
    Column {
        Text("Help Screen")
        Button(onClick = { viewModel.navigateTo(AppUiState.MAIN_SCREEN) }) {
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

    val workoutViewModel = WorkoutViewModel(fitnessApp.repository).apply {
        // todo
    }

    val preferencesViewModel = PreferencesViewModel().apply {
        // todo
    }

    FitnessTrackerTheme {
        MainScreen(mainViewModel, workoutViewModel, preferencesViewModel)
    }
}