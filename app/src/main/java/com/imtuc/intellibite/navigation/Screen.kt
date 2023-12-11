package com.imtuc.intellibite.navigation

sealed class Screen(val route: String) {
    object Splash:Screen(route = "splash_screen")
    object InputIngredients:Screen(route = "input_ingredients")
    object InputDiseases:Screen(route = "input_diseases")
    object InputAllergies:Screen(route = "input_allergies")
    object Result:Screen(route = "result/{prediction}") {
        fun passParam(
            allergies: String
        ): String {
            return "result/$allergies"
        }
    }
}

