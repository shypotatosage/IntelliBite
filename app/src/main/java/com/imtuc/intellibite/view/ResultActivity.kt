package com.imtuc.intellibite.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imtuc.intellibite.R
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.navigation.Screen
import com.imtuc.intellibite.viewmodel.MainViewModel

@Composable
fun ResultActivity(
    ingredient: List<String>,
    nutrition: List<String>,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current


    var availableRecipe = remember {
        mutableStateListOf<Recipes>()
    }

    var recipeLoading = remember {
        mutableStateOf(true)
    }

    var ownedRecipes = remember {
        mutableStateListOf<String>()
    }

    mainViewModel.recipes.observe(lifecycleOwner, Observer{
            response ->
        if (mainViewModel.recipeError.value == "Get Data Successful"){
            availableRecipe.clear()
            availableRecipe.addAll(mainViewModel.recipes.value!!)
            recipeLoading.value = false
        }else{
            recipeLoading.value = true
            Toast.makeText(context, mainViewModel.recipeError.value, Toast.LENGTH_SHORT).show()
        }
    })

    LaunchedEffect(key1 = true) {
        mainViewModel.getRecipes(ingredient, nutrition)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ingredients),
            contentDescription = "Back",
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.TopCenter)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 250.dp, start = 16.dp, end = 16.dp)
        ) {
            item {
                Text("Recipes For You")
            }
            items(availableRecipe) { recipe ->
                RecipeItem(recipe)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipes) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recipe.name,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}




