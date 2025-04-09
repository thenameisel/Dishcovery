package com.example.dishcovery.data.repository

import com.example.dishcovery.data.local.MealDao
import com.example.dishcovery.data.local.entities.MealEntity


class MealRepository(private val mealDao: MealDao) {
    suspend fun saveRecipe(meal: MealEntity): Long {
        return mealDao.insertRecipe(meal)
    }

    suspend fun getAllRecipes(): List<MealEntity> {
        return mealDao.getAllUserRecipes()
    }

    suspend fun getRecipeById(id: String): MealEntity? {
        return mealDao.getRecipeById(id)
    }
}

