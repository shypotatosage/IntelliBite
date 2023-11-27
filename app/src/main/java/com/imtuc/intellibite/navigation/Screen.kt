package com.imtuc.intellibite.navigation

sealed class Screen(val route: String) {
    object Splash:Screen(route = "splash_screen")
    object InputForm:Screen(route = "input_form")
}

