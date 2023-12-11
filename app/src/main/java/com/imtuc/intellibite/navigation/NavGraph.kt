package com.imtuc.intellibite.navigation

import InputAllergiesActivity
import InputDiseasesActivity
import InputIngredientsActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.imtuc.intellibite.view.ResultActivity
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
            route = Screen.InputIngredients.route
        ) {
            InputIngredientsActivity(navController = navController)
        }

        composable(
            route = Screen.InputDiseases.route
        ) {
            InputDiseasesActivity(navController = navController)
        }
        composable(
            route = Screen.InputAllergies.route
        ) {
            InputAllergiesActivity(navController = navController)
        }
        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("prediction") {
                    type = NavType.StringType
                }
            )
        ) {
            ResultActivity(result = it.arguments?.getString("prediction").toString(), navController)
        }
    }
}