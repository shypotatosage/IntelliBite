package com.imtuc.intellibite.navigation

sealed class Screen(val route: String) {
    object Splash:Screen(route = "splash_screen")
    object InputIngredients:Screen(route = "input_ingredients")
    object InputNutritionProfiles:Screen(route = "input_nutrition_profiles"){
        fun passParam(ingredients: List<String>): String {
            val ingredientsString = ingredients.joinToString(",")
            return "result/$ingredientsString/"
        }
    }
    object Result:Screen(route = "result/{ingredients}/{nutritions}") {
        fun passParam(
            ingredients: List<String>,
            nutritions: List<String>,
        ): String {
            val ingredientsString = ingredients.joinToString(",")
            val nutritionsString = nutritions.joinToString(",")
            return "result/$ingredientsString/$nutritionsString/"
        }
    }
}

