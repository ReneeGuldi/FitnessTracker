package com.example.fitnesstracker.ui.views
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.R
import com.example.fitnesstracker.ui.theme.FitnessTrackerTheme
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.PreferencesViewModel

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
fun PreferencesScreen(
    mainViewModel: MainViewModel,
    preferencesViewModel: PreferencesViewModel
) {
    val darkModeEnabled by preferencesViewModel.darkModeEnabled.observeAsState()
    val kilometersEnabled by preferencesViewModel.kilometersEnabled.observeAsState()
    val kilogramsEnabled by preferencesViewModel.kilogramsEnabled.observeAsState()
    val topWorkoutTypes by preferencesViewModel.topWorkoutTypes.observeAsState()
    val workoutTypes = stringArrayResource(R.array.workout_types)

    Column {
        FitnessAppBar(
            title = "Preferences",
            onBackClick = {
                mainViewModel.navigateTo(AppUiState.MAIN_SCREEN)
            },
            onHelpClick = { mainViewModel.navigateTo(AppUiState.HELP) }
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
            Text("Top Workout Types (Select up to 3):")
            TopWorkoutTypesPreference(
                allWorkoutTypes = workoutTypes,
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
fun WorkoutTypePrefDropdown(
    workoutTypes: Array<String>,
    selectedTypes: Set<String>,
    onTypesSelected: (Set<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Selected Workout Types: ${selectedTypes.joinToString()}",
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            workoutTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        val newSet = if (selectedTypes.contains(type)) {
                            selectedTypes - type
                        } else {
                            if (selectedTypes.size < 3) selectedTypes + type else selectedTypes
                        }
                        onTypesSelected(newSet)
                        expanded = false
                    }
                )
            }
        }
        selectedTypes.forEach { type ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(type, modifier = Modifier.weight(1f))
                IconButton(onClick = { onTypesSelected(selectedTypes - type) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}
@Composable
fun TopWorkoutTypesPreference(
    allWorkoutTypes: Array<String>,
    topWorkoutTypes: Set<String>,
    onTopWorkoutTypesChanged: (Set<String>) -> Unit
) {
    WorkoutTypePrefDropdown(
        workoutTypes = allWorkoutTypes,
        selectedTypes = topWorkoutTypes,
        onTypesSelected = { selectedTypes ->
            onTopWorkoutTypesChanged(selectedTypes)
        }
    )
}

