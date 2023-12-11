package com.imtuc.intellibite.navigation

sealed class Screen(val route: String) {
    object Splash:Screen(route = "splash_screen")
    object InputIngredients:Screen(route = "input_ingredients")
    object InputNutritionProfiles:Screen(route = "input_nutrition_profiles")
    object Result:Screen(route = "result/{prediction}") {
        fun passParam(
            allergies: String,
            ingredients: String,
            diseases: String,
        ): String {
            return "result/$ingredients/$diseases/$allergies"
        }
    }
}

