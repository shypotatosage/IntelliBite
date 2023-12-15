package com.imtuc.intellibite.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.viewmodel.MainViewModel

@Composable
fun DetailRecipesActivity(
    detailRecipe: String,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    var detailRecipe = remember {
        mutableStateOf(detailRecipe)
    }
}