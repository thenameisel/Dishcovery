package com.example.dishcovery.data

import androidx.lifecycle.ViewModel
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CreateRecipeViewModel(private val repository: MealRepository) : ViewModel() {
    suspend fun saveRecipe(
        name: String,
        category: String,
        area: String,
        ingredients: List<Pair<String, String>>,
        instructions: String
    ): Long {
        val recipe = MealEntity(
            name = name,
            category = category,
            area = area,
            instructions = instructions,
            ingredients = Json.encodeToString(ingredients),  // Convert to JSON
            mealThumb = ""
        )
        return repository.saveRecipe(recipe)
    }
}