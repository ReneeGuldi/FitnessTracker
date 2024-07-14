package com.example.fitnesstracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<AppUiState>(AppUiState.MAIN_SCREEN)
    val uiState: StateFlow<AppUiState> = _uiState

    fun navigateTo(state: AppUiState) {
        viewModelScope.launch {
            _uiState.value = state
        }
    }
}