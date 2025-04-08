package com.example.dishcovery.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.coroutines.launch

class MealViewModel(
    private val repository: MealRepository
) : ViewModel() {

    // Search API and save to DB
//    fun searchMeals(query: String) {
//        viewModelScope.launch {
//            val results = repository.searchMeals(query)
//            // Update UI state
//        }
//    }

    // Observe favorites from DB
    //val favorites = repository.getFavorites()

//    fun toggleFavorite(mealId: String) {
//        viewModelScope.launch {
//            repository.toggleFavorite(mealId)
//        }
//    }
}