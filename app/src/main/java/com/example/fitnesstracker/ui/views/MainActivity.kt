package com.example.fitnesstracker.ui.views

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel

class MainActivity : ComponentActivity() {
    private val workoutViewModel: WorkoutViewModel by viewModels()
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
    val uiState = mainViewModel.uiState.collectAsState()

    when (uiState.value) {
        AppUiState.MAIN_SCREEN -> MainFragment(mainViewModel)
        AppUiState.ADD_EDIT_WORKOUT -> AddEditWorkoutScreen(
            mainViewModel,
            workoutViewModel
        )
        AppUiState.VIEW_WORKOUTS -> WorkoutHistoryScreen(
            mainViewModel,
            workoutViewModel
        )
        AppUiState.USER_PREFERENCES -> PreferencesScreen(
            mainViewModel,
            preferencesViewModel
        )
        AppUiState.HELP -> HelpFragment(mainViewModel)
    }
}
@Composable
fun MainFragment(viewModel: MainViewModel) {
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
                onClick = { viewModel.navigateTo(AppUiState.ADD_EDIT_WORKOUT) },
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
                onClick = { viewModel.navigateTo(AppUiState.VIEW_WORKOUTS) },
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
                onClick = { viewModel.navigateTo(AppUiState.USER_PREFERENCES) },
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
            Text(text = "Workout 1", color = Color.Gray)
            Text(text = "Workout 2", color = Color.Gray)
            Text(text = "Workout 3", color = Color.Gray)
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val mainViewModel = MainViewModel().apply {
        // Set the initial state to MAIN_SCREEN for the preview
        navigateTo(AppUiState.MAIN_SCREEN)
    }
    val workoutViewModel = WorkoutViewModel(Application())
    val preferencesViewModel = PreferencesViewModel()

    FitnessTrackerTheme {
        MainScreen(mainViewModel, workoutViewModel, preferencesViewModel)
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