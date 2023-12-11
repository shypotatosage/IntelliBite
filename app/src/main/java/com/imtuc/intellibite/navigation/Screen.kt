package com.imtuc.intellibite.navigation

sealed class Screen(val route: String) {
    object Splash:Screen(route = "splash_screen")
    object InputIngredients:Screen(route = "input_ingredients")
    object InputNutritionProfiles:Screen(route = "input_nutrition_profiles"){
        fun passParam(
            ingredients: String,
        ): String {
            return "result/$ingredients/"
        }
    }
    object Result:Screen(route = "result/{ingredients}/{nutritions}") {
        fun passParam(
            ingredients: String,
            nutritions: String,
        ): String {
            return "result/$ingredients/$nutritions/"
        }
    }
}

