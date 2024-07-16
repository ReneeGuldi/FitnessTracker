package com.example.fitnesstracker.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesstracker.util.PreferencesManager

class PreferencesViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {



    private val _darkModeEnabled = MutableLiveData<Boolean>().apply {
        value = preferencesManager.isDarkMode
    }
    val darkModeEnabled: LiveData<Boolean> = _darkModeEnabled

    private val _kilometersEnabled = MutableLiveData<Boolean>().apply {
        value = preferencesManager.isKilometers
    }
    val kilometersEnabled: LiveData<Boolean> = _kilometersEnabled

    private val _kilogramsEnabled = MutableLiveData<Boolean>().apply {
        value = preferencesManager.isKilograms
    }
    val kilogramsEnabled: LiveData<Boolean> = _kilogramsEnabled

    private val _topWorkoutTypes = MutableLiveData<Set<String>>().apply {
        value = preferencesManager.topWorkoutTypes
    }
    val topWorkoutTypes: LiveData<Set<String>> = _topWorkoutTypes

    fun setDarkModeEnabled(enabled: Boolean) {
        preferencesManager.isDarkMode = enabled
        _darkModeEnabled.value = enabled
    }

    fun setKilometersEnabled(enabled: Boolean) {
        preferencesManager.isKilometers = enabled
        _kilometersEnabled.value = enabled
    }

    fun setKilogramsEnabled(enabled: Boolean) {
        preferencesManager.isKilograms = enabled
        _kilogramsEnabled.value = enabled
    }

    fun setTopWorkoutTypes(types: Set<String>) {
        preferencesManager.topWorkoutTypes = types
        _topWorkoutTypes.value = types
    }
}



class PreferencesViewModelFactory(private val preferencesManager: PreferencesManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}