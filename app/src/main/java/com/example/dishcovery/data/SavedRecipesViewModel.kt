package com.example.dishcovery.data

import androidx.lifecycle.ViewModel
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch


class SavedRecipesViewModel(
    private val repository: MealRepository
) : ViewModel() {

    //val recipes: State<List<MealEntity>> = repository.getAllRecipes().collectAsState(emptyList())


    // State holders
    private val _allRecipes = mutableStateOf<List<MealEntity>>(emptyList())
    private val _searchQuery = mutableStateOf("")

    // Public exposed states
    val allRecipes: State<List<MealEntity>> = _allRecipes
    val searchQuery: State<String> = _searchQuery

    val filteredRecipes: List<MealEntity> by derivedStateOf {
        if (_searchQuery.value.isBlank()) {
            _allRecipes.value
        } else {
            _allRecipes.value.filter { it.name.contains(_searchQuery.value, true) }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    init {
        viewModelScope.launch {
            _allRecipes.value = repository.getAllRecipes()
        }
    }
}