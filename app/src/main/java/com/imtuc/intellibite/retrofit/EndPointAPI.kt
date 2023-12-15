package com.imtuc.intellibite.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EndPointAPI {
    @GET("get-recipe/{id}")
    suspend fun getdetailrecipe(
        @Query("id") id: String?,
        ): Response<JsonObject>
    @GET("get-nutrition-profiles/")
    suspend fun getnutritionprofiles(
    ): Response<JsonObject>
    @GET("get-ingredients/")
    suspend fun getingredients(
    ): Response<JsonObject>
    @FormUrlEncoded
    @POST("get-recipes/")
    suspend fun getrecipes(
        @Field("ingredients") ingredients: List<String>,
        @Field("nutrition_profiles") nutrition_profiles: List<String>
    ): Response<JsonObject>
}