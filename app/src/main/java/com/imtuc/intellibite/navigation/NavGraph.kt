package com.imtuc.intellibite.navigation

import InputIngredientsActivity
import InputNutritionProfilesActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.imtuc.intellibite.view.DetailRecipesActivity
import com.imtuc.intellibite.view.ResultActivity
import com.imtuc.intellibite.view.SplashScreenActivity
import com.imtuc.intellibite.viewmodel.MainViewModel

//import com.imtuc.intellibite.viewmodel.IngredientsViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
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
            InputIngredientsActivity(navController, lifecycleOwner, mainViewModel)
        }

//        composable(
//            route = Screen.InputNutritionProfiles.route,
//            arguments = listOf(
//                navArgument("ingredient") {
//                    type = NavType.StringType
//                }
//            )
//        ) {
//            val ingredientsString = it.arguments?.getString("ingredient").orEmpty()
//            val ingredientsList = ingredientsString.split(",")
//            InputNutritionProfilesActivity(
//                ingredient = ingredientsList,
//                navController = navController,
//                lifecycleOwner = lifecycleOwner,
//                mainViewModel = mainViewModel
//            )
//        }

        composable(
            route = Screen.InputNutritionProfiles.route,
            arguments = listOf(
                navArgument("ingredient") {
                    type = NavType.StringType
                }
            )
        ) {
            InputNutritionProfilesActivity(ingredient = it.arguments?.getString("ingredient").toString(), navController, lifecycleOwner, mainViewModel)
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("results") {
                    type = NavType.StringType
                }
            )
        )  {
            ResultActivity(
                ingredient = it.arguments?.getString("ingredient").toString(),
                nutrition = it.arguments?.getString("nutrition").toString(),
                navController = navController,
                lifecycleOwner = lifecycleOwner,
                mainViewModel = mainViewModel
            )
        }

        composable(
            route = Screen.DetailRecipe.route,
            arguments = listOf(
                navArgument("detailRecipe") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailRecipesActivity(detailRecipe = it.arguments?.getString("detailRecipe").toString(), navController, lifecycleOwner, mainViewModel)
        }
    }
}