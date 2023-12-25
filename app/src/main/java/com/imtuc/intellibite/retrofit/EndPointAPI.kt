package com.imtuc.intellibite.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPointAPI {
    @GET("get-detailrecipe/{name}")
    suspend fun getdetailrecipe(
        @Path("name") id: String,
        ): Response<JsonObject>

    @GET("get-nutrition-profiles")
    suspend fun getnutritionprofiles(
    ): Response<JsonObject>

    @GET("get-ingredients")
    suspend fun getingredients(
    ): Response<JsonObject>

    @GET("get-recipes/{ingredients}/{nutrition_profiles}")
    suspend fun getrecipes(
        @Path("ingredients") ingredients: String,
        @Path("nutrition_profiles") nutrition_profiles: String
    ): Response<JsonObject>

    @GET("get-fruitvegetables/{name}")
    suspend fun getFruitVegetables(
        @Path("name") name: String,
    ): Response<JsonObject>
}