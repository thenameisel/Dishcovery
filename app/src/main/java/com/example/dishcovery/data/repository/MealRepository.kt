package com.example.dishcovery.data.repository

import com.example.dishcovery.data.local.AppDatabase
import com.example.dishcovery.data.local.MealDao
import com.example.dishcovery.data.local.entities.MealEntity
import com.example.dishcovery.data.remote.MealDbApiService
import kotlinx.coroutines.flow.Flow


class MealRepository(private val mealDao: MealDao) {
    suspend fun saveRecipe(meal: MealEntity): Long {
        return mealDao.insertRecipe(meal)
    }

    suspend fun getAllRecipes(): List<MealEntity> {
        return mealDao.getAllUserRecipes()
    }
}

//Old class??? I dont know.
//class MealRepository(
//    private val api: MealDbApiService,
//    private val db: AppDatabase
//) {
//    private val dao = db.mealDao()
//
//    // Search meals (API → Cache to DB)
//    suspend fun searchMeals(query: String): List<MealEntity> {
//        val apiResponse = api.searchMeals(query).meals ?: emptyList()
//        apiResponse.forEach { dao.insertMeal(it) }
//        return apiResponse
//    }
//
//    // Get favorites (DB only)
//    fun getFavorites(): Flow<List<MealEntity>> = dao.getFavorites()
//
//    // Toggle favorite status
//    suspend fun toggleFavorite(mealId: String) {
//        val meal = dao.getMealById(mealId) ?: return
//        dao.insertMeal(meal.copy(isFavorite = !meal.isFavorite))
//    }
//}