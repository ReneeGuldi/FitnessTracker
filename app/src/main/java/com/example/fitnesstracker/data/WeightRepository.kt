package com.example.fitnesstracker.data

import kotlinx.coroutines.flow.Flow

class WeightRepository(private val weightEntryDao: WeightEntryDao) {
    val allWeightEntries: Flow<List<WeightEntries>> = weightEntryDao.getAllWeightEntries()

    suspend fun insert(weightEntry: WeightEntries) {
        weightEntryDao.insert(weightEntry)
    }
    suspend fun delete(weightEntry: WeightEntries) {
        weightEntryDao.delete(weightEntry)
    }

}