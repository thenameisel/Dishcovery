package com.example.dishcovery.data

import androidx.lifecycle.ViewModel
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.coroutines.flow.Flow

class SavedRecipesViewModel(private val repository: MealRepository) : ViewModel() {
    //val userRecipes: Flow<List<MealEntity>> = repository.getUserRecipes()
}