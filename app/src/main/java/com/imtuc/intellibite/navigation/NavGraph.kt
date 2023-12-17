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
import com.imtuc.intellibite.view.ImageClassificationActivity
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
        startDestination = Screen.ImageClassification.route
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
                navArgument("ingredients") {
                    type = NavType.StringType
                }
            )
        ) {
            InputNutritionProfilesActivity(ingredient = it.arguments?.getString("ingredients").toString(), navController, lifecycleOwner, mainViewModel)
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("ingredients") {
                    type = NavType.StringType
                },
                navArgument("nutritions") {
                    type = NavType.StringType
                }
            )
        )  {
            ResultActivity(
                ingredient = it.arguments?.getString("ingredients").toString(),
                nutrition = it.arguments?.getString("nutritions").toString(),
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

        composable(
            route = Screen.ImageClassification.route
        ) {
            ImageClassificationActivity()
        }
    }
}