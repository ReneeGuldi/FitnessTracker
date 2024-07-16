package com.example.fitnesstracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workout_table ORDER BY date DESC")
    fun getAllWorkOuts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table ORDER BY date DESC LIMIT 10")
    fun getRecentWorkouts(): LiveData<List<Workout>>
}