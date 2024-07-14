package com.example.fitnesstracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.util.PreferencesManager

class PreferencesViewModel : ViewModel() {
    private lateinit var preferencesManager: PreferencesManager

    private val _darkModeEnabled = MutableLiveData<Boolean>()
    val darkModeEnabled: LiveData<Boolean> = _darkModeEnabled

    private val _kilometersEnabled = MutableLiveData<Boolean>()
    val kilometersEnabled: LiveData<Boolean> = _kilometersEnabled

    private val _kilogramsEnabled = MutableLiveData<Boolean>()
    val kilogramsEnabled: LiveData<Boolean> = _kilogramsEnabled

    private val _topWorkoutTypes = MutableLiveData<Set<String>>()
    val topWorkoutTypes: LiveData<Set<String>> = _topWorkoutTypes

    fun init(preferencesManager: PreferencesManager) {
        this.preferencesManager = preferencesManager

        _darkModeEnabled.value = preferencesManager.isDarkMode
        _kilometersEnabled.value = preferencesManager.isKilometers
        _kilogramsEnabled.value = preferencesManager.isKilograms
        _topWorkoutTypes.value = preferencesManager.topWorkoutTypes
    }

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