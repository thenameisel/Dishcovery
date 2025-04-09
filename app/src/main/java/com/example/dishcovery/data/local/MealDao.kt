package com.example.dishcovery.data.local
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.dishcovery.data.local.entities.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}

@Dao
interface MealDao {
    @Insert
    suspend fun insertRecipe(recipe: MealEntity): Long  // Returns generated ID

    @Query("SELECT * FROM user_recipes")
    suspend fun getAllUserRecipes(): List<MealEntity>  // Observe changes

    @Delete
    suspend fun deleteRecipe(recipe: MealEntity)

    @Query("SELECT * FROM user_recipes WHERE id = :id")
    suspend fun getRecipeById(id: String): MealEntity?
}
