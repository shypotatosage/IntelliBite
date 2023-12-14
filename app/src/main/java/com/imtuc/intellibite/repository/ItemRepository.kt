package com.imtuc.intellibite.repository

import javax.inject.Inject
import com.imtuc.intellibite.retrofit.EndPointAPI


class ItemRepository @Inject constructor(
    private val api: EndPointAPI
) {
    suspend fun getRecipes(
        ingredients: List<String>,
        nutritionProfiles: List<String>,
    ) = api.getrecipes(ingredients, nutritionProfiles)

//    suspend fun getIngredients(
//        ingredients: List<String>,
//        ) = api.getrecipes(ingredients)
}