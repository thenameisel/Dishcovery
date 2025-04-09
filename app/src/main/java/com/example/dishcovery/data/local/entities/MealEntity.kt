package com.example.dishcovery.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "user_recipes")  // Renamed table
@Serializable
data class MealEntity(
    @PrimaryKey(autoGenerate = true)  // Auto ID for user-created recipes
    val id: Long = 0,  // Local DB-generated ID

    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val ingredients: String,
    val createdAt: Long = System.currentTimeMillis(),
    val mealThumb: String
)

