package com.example.fitnesstracker.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isDarkMode: Boolean
        get() = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_DARK_MODE, value)}

    var isKilometers: Boolean
        get() = sharedPreferences.getBoolean(KEY_KILOMETERS, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_KILOMETERS, value)}

    var isKilograms: Boolean
        get() = sharedPreferences.getBoolean(KEY_KILOGRAMS, false)
        set(value) = sharedPreferences.edit { putBoolean(KEY_KILOGRAMS, value)}

    var topWorkoutTypes: Set<String>
        get() = sharedPreferences.getStringSet(KEY_TOP_WORKOUT_TYPES, emptySet()) ?: emptySet()
        set(value) = sharedPreferences.edit { putStringSet(KEY_TOP_WORKOUT_TYPES, value)}


    companion object {
        private const val PREFS_NAME = "fitness_prefs"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_KILOMETERS = "kilometers"
        private const val KEY_KILOGRAMS = "kilograms"
        private const val KEY_TOP_WORKOUT_TYPES = "top_workout_types"
    }
}