package com.imtuc.intellibite.repository

import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import javax.inject.Inject
import com.imtuc.intellibite.retrofit.EndPointAPI


class ItemRepository @Inject constructor(
    private val api: EndPointAPI
) {
    suspend fun getRecipes(
        ingredients: List<Ingredients>,
        nutritionProfiles: List<Nutrition_Profiles>,
    ) = api.getrecipes(ingredients, nutritionProfiles)

    suspend fun getIngredients(

    ) = api.getingredients()

    suspend fun getNutritionProfile(

    ) = api.getnutritionprofiles()
    suspend fun getDetailRecipe(
        id: String
    ) = api.getdetailrecipe(id)

}