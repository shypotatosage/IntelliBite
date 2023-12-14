package com.imtuc.intellibite.model

data class Recipes (
    var id: String,
    var name: String,
    var making_time_in_minutes: Int,
    var servings: Int,
    var calories_per_portion: Int,
    var fats_per_portion: Int,
    var proteins_per_portion: Int,
    var carbs_per_portion: Int,
    var ingredients: List<Ingredients>,
    var nutritions: List<Nutrition_Profiles>
)