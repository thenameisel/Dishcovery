package com.example.dishcovery.data.local
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.dishcovery.data.local.entities.MealEntity
import kotlinx.coroutines.flow.Flow

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

//Old Dao??? No idea
//@Dao
//interface MealDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMeal(meal: MealEntity)
//
//    @Query("SELECT * FROM meals WHERE isFavorite = 1")
//    fun getFavorites(): Flow<List<MealEntity>>
//
//    @Query("SELECT * FROM meals WHERE id = :id")
//    suspend fun getMealById(id: String): MealEntity?
//}