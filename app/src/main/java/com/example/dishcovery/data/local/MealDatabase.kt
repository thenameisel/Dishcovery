package com.example.dishcovery.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dishcovery.data.Converters
import com.example.dishcovery.data.local.entities.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false // Set to true if you want schema exports
)
@TypeConverters(Converters::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getDatabase(context: Context): MealDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_database"
                )
                    .fallbackToDestructiveMigration() // Remove in production
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}