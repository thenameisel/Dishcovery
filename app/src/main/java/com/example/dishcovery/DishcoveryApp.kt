package com.example.dishcovery

import com.example.dishcovery.data.local.MealDatabase
import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.dishcovery.data.repository.MealRepository

class DishcoveryApp : Application() {
    val database by lazy {
        MealDatabase.getDatabase(this).also {
            Log.d("DB_INIT", "Database initialized")
        }
    }

    val repository by lazy {
        MealRepository(database.mealDao())
    }

    override fun onCreate() {
        super.onCreate()
        // Optional: Initialize here if you need early access
        Log.d("APP_LIFECYCLE", "Application created")
    }
}