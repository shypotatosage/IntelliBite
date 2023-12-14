package com.imtuc.intellibite.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPointAPI {
    @GET("/getRecipes/{ingredients}/{nutritionProfiles}")
    suspend fun getrecipes(
        @Path("ingredients") ingredients: List<String>,
        @Path("nutritionProfiles") nutritionProfiles: List<String>,
    ): Response<JsonObject>
    @GET("/getNutritionProfiles")
    suspend fun getnutritionprofiles(): Response<JsonObject>
    @GET("/getIngredients")
    suspend fun getingredients(
        @Path("ingredients") ingredients: List<String>,
    ): Response<JsonObject>
}