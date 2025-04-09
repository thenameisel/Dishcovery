package com.example.dishcovery

import com.example.dishcovery.data.local.MealDatabase
import android.app.Application
import android.util.Log
import com.example.dishcovery.data.remote.MealApiService
import com.example.dishcovery.data.repository.MealRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DishcoveryApp : Application() {
    val database by lazy {
        MealDatabase.getDatabase(this).also {
            Log.d("DB_INIT", "Database initialized")
        }
    }

    val repository by lazy {
        MealRepository(database.mealDao())
    }

    val apiService: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("APP_LIFECYCLE", "Application created")
    }
}