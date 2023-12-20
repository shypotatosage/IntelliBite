package com.imtuc.intellibite.repository

import javax.inject.Inject
import com.imtuc.intellibite.retrofit.EndPointAPI


class ItemRepository @Inject constructor(
    private val api: EndPointAPI
) {
    suspend fun getRecipes(
        ingredients: String,
        nutritionProfiles: String,
    ) = api.getrecipes(ingredients, nutritionProfiles)

    suspend fun getIngredients(

    ) = api.getingredients()

    suspend fun getNutritionProfile(

    ) = api.getnutritionprofiles()

    suspend fun getDetailRecipe(
        id: String
    ) = api.getdetailrecipe(id)

    suspend fun getFruitVegetables(
        name: String
    ) = api.getFruitVegetables(name)

}