package com.imtuc.intellibite.model

data class Recipes (
    var name: String,
    var making_time_in_minutes: Int,
    var servings: Int,
    var calories_per_portion: Int = 0,
    var fats_per_portion: Int = 0,
    var proteins_per_portion: Int = 0,
    var carbs_per_portion: Int = 0,
    var ingredients: List<Ingredients> = arrayListOf(),
    var nutritions: List<Nutrition_Profiles> = arrayListOf(),
    var steps: List<Steps> = arrayListOf()
)