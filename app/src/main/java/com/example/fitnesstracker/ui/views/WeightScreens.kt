package com.example.fitnesstracker.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitnesstracker.data.WeightEntries
import com.example.fitnesstracker.util.PreferencesManager
import com.example.fitnesstracker.viewmodel.AppUiState
import com.example.fitnesstracker.viewmodel.MainViewModel
import com.example.fitnesstracker.viewmodel.WeightViewModel

@Composable
fun AddWeightEntryScreen(
    weightViewModel: WeightViewModel,
    viewModel: MainViewModel,
    preferencesManager: PreferencesManager
) {
    var weight by remember { mutableStateOf("") }
    val isKilograms by remember { mutableStateOf(preferencesManager.isKilograms) }
    val weightUnit = if (isKilograms) "kg" else "lbs"

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            FitnessAppBar(
                title = "Add Weight Entry",
                onBackClick = { viewModel.navigateTo(AppUiState.WEIGHT_SCREEN) },
                onHelpClick = { viewModel.navigateTo(AppUiState.HELP) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Enter your weight ($weightUnit)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val weightFloat = weight.toFloatOrNull()
                        if (weightFloat != null) {
                            val weightInKg = if (isKilograms) weightFloat else weightFloat * 0.453592f // Convert lbs to kg
                            weightViewModel.insert(weightInKg)
                            weight = ""
                        }
                        viewModel.navigateTo(AppUiState.WEIGHT_SCREEN)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Weight Entry")
                }
            }
        }
    }
}


@Composable
fun WeightEntriesScreen(
    weightViewModel: WeightViewModel,
    mainViewModel: MainViewModel,
    onAddWeightEntry: () -> Unit,
    preferencesManager: PreferencesManager
) {
    val weightEntries by weightViewModel.allWeightEntries.observeAsState(emptyList())
    val isKilograms by remember { mutableStateOf(preferencesManager.isKilograms) }

    Scaffold(
        topBar = {
            FitnessAppBar(
                title = "Weight Entries",
                onBackClick = { mainViewModel.navigateTo(AppUiState.MAIN_SCREEN) },
                onHelpClick = { mainViewModel.navigateTo(AppUiState.HELP) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddWeightEntry) {
                Icon(Icons.Default.Add, contentDescription = "Add Weight Entry")
            }
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(weightEntries) { weightEntry ->
                WeightEntryItem(
                    weightEntry = weightEntry,
                    isKilograms = isKilograms,
                    onDeleteClick = { weight ->
                        weightViewModel.delete(weight)
                    }
                )
            }
        }
    }
}


@Composable
fun WeightEntryItem(
    weightEntry: WeightEntries,
    isKilograms: Boolean,
    onDeleteClick: (WeightEntries) -> Unit // Update to use WeightEntries
) {
    val weightUnit = if (isKilograms) "kg" else "lbs"
    val weight = if (isKilograms) {
        weightEntry.weight
    } else {
        weightEntry.weight * 2.20462 // Convert kg to lbs
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Weight: %.2f $weightUnit".format(weight),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Date: ${weightEntry.date}"
            )
        }
        Button(onClick = { onDeleteClick(weightEntry) }) {
            Text(text = "Delete")
        }
    }
}