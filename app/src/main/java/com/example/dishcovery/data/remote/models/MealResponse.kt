package com.example.dishcovery.data.remote.models

import com.example.dishcovery.data.local.entities.MealEntity
import com.squareup.moshi.Json
//Is this being used at all or can it go?

data class MealResponse(
    @Json(name = "meals") val meals: List<MealEntity>?
)