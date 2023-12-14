package com.imtuc.intellibite.repository

import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.model.Steps
import javax.inject.Inject
import com.imtuc.intellibite.retrofit.EndPointAPI


class ItemRepository @Inject constructor(
    private val api: EndPointAPI
) {
    suspend fun getRecipes(
        ingredients: List<Ingredients>,
        nutritionProfiles: List<Nutrition_Profiles>,
        step: List<Steps>,
    ) = api.getrecipes(ingredients, nutritionProfiles, step)

    suspend fun getIngredients(

    ) = api.getingredients()

    suspend fun getNutritionProfile(

    ) = api.getnutritionprofiles()
    suspend fun getDetailRecipe(
        id: String
    ) = api.getdetailrecipe(id)

}