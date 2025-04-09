package com.example.dishcovery.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MealApiService {
    // For searching recipes by name
    @GET("search.php")
    suspend fun searchRecipes(
        @Query("s") query: String
    ): MealResponse

    //https://www.themealdb.com/api/json/v1/1/filter.php?i
    // For getting all recipes
    @GET("filter.php?i=")
    suspend fun getAllRecipes(
    ): MealResponse

    // For getting full recipe details
    @GET("lookup.php")
    suspend fun getRecipeDetails(
        @Query("i") recipeId: String
    ): MealDetailResponse

    @JsonClass(generateAdapter = true)
    data class MealDetailResponse(
        @field:Json(name = "meals") val meals: List<MealDetail>?
    )

    @JsonClass(generateAdapter = true)
    data class MealDetail(
        @field:Json(name = "idMeal") val idMeal: String,
        @field:Json(name = "strMeal") val strMeal: String,
        @field:Json(name = "strCategory") val strCategory: String?,
        @field:Json(name = "strArea") val strArea: String?,
        @field:Json(name = "strInstructions") val strInstructions: String?,
        @field:Json(name = "strMealThumb") val strMealThumb: String?,
        @field:Json(name = "strYoutube") val strYoutube: String?,
        @field:Json(name = "strTags") val strTags: String?,

        // Ingredients 1-20
        @field:Json(name = "strIngredient1") val strIngredient1: String?,
        @field:Json(name = "strIngredient2") val strIngredient2: String?,
        @field:Json(name = "strIngredient3") val strIngredient3: String?,
        @field:Json(name = "strIngredient4") val strIngredient4: String?,
        @field:Json(name = "strIngredient5") val strIngredient5: String?,
        @field:Json(name = "strIngredient6") val strIngredient6: String?,
        @field:Json(name = "strIngredient7") val strIngredient7: String?,
        @field:Json(name = "strIngredient8") val strIngredient8: String?,
        @field:Json(name = "strIngredient9") val strIngredient9: String?,
        @field:Json(name = "strIngredient10") val strIngredient10: String?,
        @field:Json(name = "strIngredient11") val strIngredient11: String?,
        @field:Json(name = "strIngredient12") val strIngredient12: String?,
        @field:Json(name = "strIngredient13") val strIngredient13: String?,
        @field:Json(name = "strIngredient14") val strIngredient14: String?,
        @field:Json(name = "strIngredient15") val strIngredient15: String?,
        @field:Json(name = "strIngredient16") val strIngredient16: String?,
        @field:Json(name = "strIngredient17") val strIngredient17: String?,
        @field:Json(name = "strIngredient18") val strIngredient18: String?,
        @field:Json(name = "strIngredient19") val strIngredient19: String?,
        @field:Json(name = "strIngredient20") val strIngredient20: String?,

        // Measures 1-20
        @field:Json(name = "strMeasure1") val strMeasure1: String?,
        @field:Json(name = "strMeasure2") val strMeasure2: String?,
        @field:Json(name = "strMeasure3") val strMeasure3: String?,
        @field:Json(name = "strMeasure4") val strMeasure4: String?,
        @field:Json(name = "strMeasure5") val strMeasure5: String?,
        @field:Json(name = "strMeasure6") val strMeasure6: String?,
        @field:Json(name = "strMeasure7") val strMeasure7: String?,
        @field:Json(name = "strMeasure8") val strMeasure8: String?,
        @field:Json(name = "strMeasure9") val strMeasure9: String?,
        @field:Json(name = "strMeasure10") val strMeasure10: String?,
        @field:Json(name = "strMeasure11") val strMeasure11: String?,
        @field:Json(name = "strMeasure12") val strMeasure12: String?,
        @field:Json(name = "strMeasure13") val strMeasure13: String?,
        @field:Json(name = "strMeasure14") val strMeasure14: String?,
        @field:Json(name = "strMeasure15") val strMeasure15: String?,
        @field:Json(name = "strMeasure16") val strMeasure16: String?,
        @field:Json(name = "strMeasure17") val strMeasure17: String?,
        @field:Json(name = "strMeasure18") val strMeasure18: String?,
        @field:Json(name = "strMeasure19") val strMeasure19: String?,
        @field:Json(name = "strMeasure20") val strMeasure20: String?
    ) {
        fun getIngredientsList(): List<Pair<String?, String>> {
            return listOf(
                strIngredient1 to strMeasure1,
                strIngredient2 to strMeasure2,
                strIngredient3 to strMeasure3,
                strIngredient4 to strMeasure4,
                strIngredient5 to strMeasure5,
                strIngredient6 to strMeasure6,
                strIngredient7 to strMeasure7,
                strIngredient8 to strMeasure8,
                strIngredient9 to strMeasure9,
                strIngredient10 to strMeasure10,
                strIngredient11 to strMeasure11,
                strIngredient12 to strMeasure12,
                strIngredient13 to strMeasure13,
                strIngredient14 to strMeasure14,
                strIngredient15 to strMeasure15,
                strIngredient16 to strMeasure16,
                strIngredient17 to strMeasure17,
                strIngredient18 to strMeasure18,
                strIngredient19 to strMeasure19,
                strIngredient20 to strMeasure20
            ).filter { (ingredient, _) -> !ingredient.isNullOrBlank() }
                .map { (ingredient, measure) -> ingredient to (measure ?: "") }
        }
    }

    // Response classes
    data class MealResponse(
        @field:Json(name = "meals") val meals: List<Meal>?
    )


    data class Meal(
        @field:Json(name = "idMeal") val idMeal: String,
        @field:Json(name = "strMeal") val strMeal: String,
        @field:Json(name = "strMealThumb") val strMealThumb: String?,
        @field:Json(name = "strCategory") val strCategory: String? = null,
        @field:Json(name = "strArea") val strArea: String? = null
    )

}
object MealApi {
    val service: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}