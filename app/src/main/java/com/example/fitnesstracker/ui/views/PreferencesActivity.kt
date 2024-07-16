package com.example.fitnesstracker.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel
import com.example.fitnesstracker.viewmodel.WorkoutViewModel

class PreferencesActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val preferencesViewModel: PreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessTrackerTheme {
                PreferencesScreen(mainViewModel, preferencesViewModel)
            }
        }
    }
}

@Composable
fun PreferencesScreen(mainViewModel: MainViewModel, preferencesViewModel: PreferencesViewModel) {
    val darkModeEnabled by preferencesViewModel.darkModeEnabled.observeAsState()
    val kilometersEnabled by preferencesViewModel.kilometersEnabled.observeAsState()
    val kilogramsEnabled by preferencesViewModel.kilogramsEnabled.observeAsState()
    val topWorkoutTypes by preferencesViewModel.topWorkoutTypes.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FitnessAppBar(
            title = "Preferences",
            onBackClick = {
                mainViewModel.navigateTo(AppUiState.MAIN_SCREEN)
            }
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SwitchPreference(
                title = "Dark Mode",
                checked = darkModeEnabled ?: false,
                onCheckedChange = { preferencesViewModel.setDarkModeEnabled(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SwitchPreference(
                title = "Show Kilometers",
                checked = kilometersEnabled ?: false,
                onCheckedChange = { preferencesViewModel.setKilometersEnabled(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SwitchPreference(
                title = "Show Kilograms",
                checked = kilogramsEnabled ?: false,
                onCheckedChange = { preferencesViewModel.setKilogramsEnabled(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Top Workout Types:")
            TopWorkoutTypesPreference(
                topWorkoutTypes = topWorkoutTypes ?: emptySet(),
                onTopWorkoutTypesChanged = { preferencesViewModel.setTopWorkoutTypes(it) }
            )
        }
    }
}

@Composable
fun SwitchPreference(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun TopWorkoutTypesPreference(
    topWorkoutTypes: Set<String>,
    onTopWorkoutTypesChanged: (Set<String>) -> Unit
) {
    // Placeholder implementation for the top workout types selection
    val allWorkoutTypes = listOf("Running", "Cycling", "Swimming", "Weightlifting", "Yoga")

    Column {
        allWorkoutTypes.forEach { type ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(type)
                Switch(
                    checked = topWorkoutTypes.contains(type),
                    onCheckedChange = {
                        val newSet = if (it) {
                            topWorkoutTypes + type
                        } else {
                            topWorkoutTypes - type
                        }
                        onTopWorkoutTypesChanged(newSet)
                    }
                )
            }
        }
    }
}