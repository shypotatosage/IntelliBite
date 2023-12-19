package com.imtuc.intellibite.model

data class Recipe_Ingredients(
    var id: String,
    var quantity: Int,
    var description: String,
    var description_steps: String?,
    var unit: String,
    var recipe_id: Int,
    var ingredient_id: Int,
    var name: String,
)