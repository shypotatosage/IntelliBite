package com.imtuc.intellibite.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.imtuc.intellibite.view.InputFormActivity
import com.imtuc.intellibite.view.SplashScreenActivity

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreenActivity(navController = navController)
        }

        composable(
            route = Screen.InputForm.route
        ) {
            InputFormActivity(navController = navController)
        }
    }
}