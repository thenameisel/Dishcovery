package com.example.dishcovery.data

import androidx.room.TypeConverter
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.remote.MealApiService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromIngredientsList(ingredients: List<Pair<String, String>>): String {
        return Json.encodeToString(ingredients)
    }
    @TypeConverter
    fun toIngredients(ingredientsString: String): List<Pair<String, String>> {
        return try {
            Json.decodeFromString(ingredientsString)
        } catch (e: Exception) {
            emptyList() // Return empty list if parsing fails
        }
    }

}