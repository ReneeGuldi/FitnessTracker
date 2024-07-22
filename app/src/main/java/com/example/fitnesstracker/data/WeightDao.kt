package com.example.fitnesstracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.fitnesstracker.data.WeightEntries


@Dao
interface WeightEntryDao {
    @Query("SELECT * FROM weight_entries ORDER BY date DESC")
    fun getAllWeightEntries(): Flow<List<WeightEntries>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weightEntry: WeightEntries)

    @Delete
    suspend fun delete(weightEntry: WeightEntries)

}