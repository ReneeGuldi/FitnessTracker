package com.example.fitnesstracker.app

import android.app.Application
import com.example.fitnesstracker.data.AppDatabase

class FitnessApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}



