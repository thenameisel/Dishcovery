package com.example.dishcovery.data

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.remote.MealApiService
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RecipeDetailViewModel(
    private val repository: MealRepository,
    private val apiService: MealApiService
) : ViewModel() {
    private val _recipe = mutableStateOf<MealEntity?>(null)
    val recipe: State<MealEntity?> = _recipe

    private val _isLoading = mutableStateOf(false)


    private val _error = mutableStateOf<String?>(null)


    val ingredients: List<Pair<String, String>>
        get() = _recipe.value?.ingredients?.let {
            try {
                Json.decodeFromString<List<Pair<String, String>>>(it)
            } catch (e: Exception) {
                emptyList()
            }
        } ?: emptyList()

    fun loadRecipe(recipeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                var loadedRecipe = repository.getRecipeById(recipeId)

                if (loadedRecipe == null) {
                    loadedRecipe = fetchFromApi(recipeId)?.also {

                    }
                }

                _recipe.value = loadedRecipe
            } catch (e: Exception) {
                _error.value = "Failed to load recipe: ${e.localizedMessage}"
                Log.e("RecipeDetail", "Error loading recipe", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchFromApi(recipeId: String): MealEntity? {
        return apiService.getRecipeDetails(recipeId).meals?.firstOrNull()?.let { apiMeal ->
            MealEntity(
                id = apiMeal.idMeal.toLong(),
                name = apiMeal.strMeal,
                category = apiMeal.strCategory ?: "Unknown",
                area = apiMeal.strArea ?: "Unknown",
                ingredients = Json.encodeToString(apiMeal.getIngredientsList()),
                instructions = apiMeal.strInstructions ?: "",
                mealThumb = apiMeal.strMealThumb ?: ""
            )
        }
    }

}


class RecipeDetailViewModelFactory(
    private val repository: MealRepository,
    private val apiService: MealApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeDetailViewModel(repository, apiService) as T
    }
}