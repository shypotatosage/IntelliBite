package com.imtuc.intellibite.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.imtuc.intellibite.R
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.viewmodel.MainViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.model.Recipe_Ingredients
import com.imtuc.intellibite.model.Steps

@Composable
fun DetailRecipesActivity(
    name: String,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    var detailrecipe = remember {
        mutableStateListOf<Recipes>()
    }

    var detailrecipeloading = remember {
        mutableStateOf(true)
    }

    var recipe_name = remember {
        mutableStateOf("")
    }

    var recipe_making_time_in_minutes = remember {
        mutableStateOf(0)
    }

    var recipe_servings = remember {
        mutableStateOf(0)
    }

    var recipe_calories_per_portion = remember {
        mutableStateOf(0)
    }

    var recipe_fats_per_portion = remember {
        mutableStateOf(0)
    }

    var recipe_proteins_per_portion = remember {
        mutableStateOf(0)
    }

    var recipe_carbs_per_portion = remember {
        mutableStateOf(0)
    }

    var recipe_ingredients = remember {
        mutableStateListOf<Ingredients>()
    }

    var recipe_nutritions = remember {
        mutableStateListOf<Nutrition_Profiles>()
    }

    var recipe_steps = remember {
        mutableStateListOf<Steps>()
    }

    mainViewModel.getDetailRecipe(name)

    mainViewModel.detailRecipe.observe(lifecycleOwner, Observer{
            response ->
        if (response.name.isNotEmpty()) {
            recipe_name.value = response.name
            recipe_making_time_in_minutes.value = response.making_time_in_minutes
            recipe_calories_per_portion.value = response.calories_per_portion
            recipe_servings.value = response.servings
            recipe_carbs_per_portion.value = response.carbs_per_portion
            recipe_fats_per_portion.value = response.fats_per_portion
            recipe_proteins_per_portion.value = response.proteins_per_portion

            recipe_ingredients.clear()
            recipe_nutritions.clear()
            recipe_steps.clear()

            recipe_ingredients.addAll(response.ingredients)
            recipe_nutritions.addAll(response.nutritions)
            recipe_steps.addAll(response.steps)

            detailrecipeloading.value = false
        }else{
            Log.d("The Detail Recipe is Empty", detailrecipe.toString())
        }


    })

    LaunchedEffect(key1 = true) {
        mainViewModel.getDetailRecipe(name)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .padding(top = 100.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
//                .verticalScroll(rememberScrollState())
        ) {
            item {
                Text("${recipe_name.value}", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
            item {
                Text("Servings : ${recipe_servings.value}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            item {
                Text("Time Needed : ${recipe_making_time_in_minutes.value} Minutes", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            item {
                Text("Composition", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Calories : ${recipe_calories_per_portion.value}")
                    Text("Fat : ${recipe_fats_per_portion.value}g")
                    Text("Carbohydrate : ${recipe_carbs_per_portion.value}g")
                    Text("Protein : ${recipe_proteins_per_portion.value}g")
                }
            }
            item {
                Text("Nutrition Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
            }

            items(recipe_nutritions) { recipe_nutritions ->
                DetailRecipeNutritions(nutrition = recipe_nutritions)
            }

            item {
                Text("Ingredients", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
            }

            items(recipe_ingredients) { recipe_ingredients ->
                DetailRecipeIngredients(ingredients = recipe_ingredients)
            }

            item {
                Text("Steps", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
            }

            items(recipe_steps) { recipe_step ->
                DetailRecipeSteps(steps = recipe_step, recipe_steps.indexOf(recipe_step) + 1)
            }


            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DetailRecipeNutritions(nutrition: Nutrition_Profiles) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("• ${nutrition.name}"
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
        )
    }

}
@Composable
fun DetailRecipeIngredients(ingredients: Ingredients) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("• ${ingredients.name}"
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
            )
        }

}
@Composable
fun DetailRecipeSteps(steps: Steps, count: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            "Step ${count}",
            textDecoration = TextDecoration.Underline
        )
        Text("${steps.description}")
    }
}