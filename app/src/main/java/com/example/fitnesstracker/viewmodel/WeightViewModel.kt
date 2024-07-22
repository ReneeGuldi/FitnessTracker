package com.example.fitnesstracker.viewmodel

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnesstracker.data.WeightEntries
import com.example.fitnesstracker.data.WeightRepository
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

class WeightViewModel(private val repository: WeightRepository) : ViewModel() {
    val allWeightEntries: LiveData<List<WeightEntries>> = repository.allWeightEntries.asLiveData()

    fun insert(weight: Float) = viewModelScope.launch {
        val date = getCurrentDate()
        val weightEntry = WeightEntries(weight = weight, date = date)
        repository.insert(weightEntry)
    }

    fun delete(weightEntry: WeightEntries) = viewModelScope.launch {
        repository.delete(weightEntry)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}

class WeightViewModelFactory(private val repository: WeightRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeightViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}