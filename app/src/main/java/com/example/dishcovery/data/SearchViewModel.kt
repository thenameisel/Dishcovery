package com.example.dishcovery.data

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.remote.MealApiService
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.dishcovery.data.remote.MealApi
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MealRepository,
    private val apiServiceMeal: MealApiService
) : ViewModel() {
    private val apiService = MealApi.service
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _recipes = mutableStateOf<List<MealEntity>>(emptyList())
    val recipes: State<List<MealEntity>> = _recipes

    // Conversion extension
//    private fun MealApiService.Meal.toMealEntity(): MealEntity = MealEntity(
//        id = idMeal.toLong(),
//        name = strMeal,
//        category = strCategory,
//        area = strArea,
//        ingredients = "", // Will be filled from details API
//        instructions = "", // Will be filled from details API
//        mealThumb = strMealThumb
//    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun searchRecipes(query: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiResponse = if (query.isBlank()) {
                    Log.d("API_DEBUG", "Fetching default recipes")
                    apiService.getAllRecipes()
                } else {
                    Log.d("API_DEBUG", "Searching for: $query")
                    apiService.searchRecipes(query)
                }

                _recipes.value = apiResponse.meals?.map { apiMeal ->
                    MealEntity(
                        id = apiMeal.idMeal.toLong(),
                        name = apiMeal.strMeal,
                        category = apiMeal.strCategory ?: "Unknown", // From search.php
                        area = apiMeal.strArea ?: "Unknown",         // From search.php
                        ingredients = "",
                        instructions = "",
                        mealThumb = apiMeal.strMealThumb ?: ""
                    )
                } ?: emptyList()

                Log.d("API_DEBUG", "Received ${_recipes.value.size} items")

            } catch (e: Exception) {
                Log.e("API_ERROR", "Search failed", e)
                _recipes.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun searchRecipes(query: String = "") {
//        viewModelScope.launch {
//            try {
//                val apiResponse = if (query.isBlank()) {
//                    apiService.getAllRecipes()
//                } else {
//                    apiService.searchRecipes(query)
//                }
//                _recipes.value = apiResponse.meals?.map { apiMeal ->
//                    MealEntity(
//                        id = apiMeal.idMeal.toLong(),
//                        name = apiMeal.strMeal,
//                        category = apiMeal.strCategory ?: "Unknown",
//                        area = apiMeal.strArea ?: "Unknown",
//                        ingredients = "", // Will need separate API call
//                        instructions = "", // Will need separate API call
//                        mealThumb = apiMeal.strMealThumb ?: ""
//                    )
//                } ?: emptyList()
//                Log.d("API_DEBUG", "Displaying ${_recipes.value.size} items")
//            } catch (e: Exception) {
//                _recipes.value = emptyList()
//            }
//        }
//    }



}

