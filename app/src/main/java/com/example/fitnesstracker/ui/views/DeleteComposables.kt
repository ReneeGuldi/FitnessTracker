package com.example.fitnesstracker.ui.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.fitnesstracker.data.Workout

@Composable
fun ConfirmDeleteDialog(
    workout: Workout?,
    onConfirm: (Workout) -> Unit,
    onDismiss: () -> Unit
) {
    if (workout != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Confirm Delete")
            },
            text = {
                Text(text = "Are you sure you want to delete this workout?")
            },
            confirmButton = {
                Button(onClick = { onConfirm(workout) }) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}