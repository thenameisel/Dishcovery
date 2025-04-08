package com.example.dishcovery.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromIngredientsList(ingredients: List<Pair<String, String>>): String {
        return Json.encodeToString(ingredients)
    }

    @TypeConverter
    fun toIngredientsList(ingredientsString: String): List<Pair<String, String>> {
        return Json.decodeFromString(ingredientsString)
    }
}