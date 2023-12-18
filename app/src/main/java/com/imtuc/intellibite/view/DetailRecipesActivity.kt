package com.imtuc.intellibite.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.imtuc.intellibite.R
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.navigation.Screen
import com.imtuc.intellibite.viewmodel.MainViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext

@Composable
fun DetailRecipesActivity(
    recipeid: String,
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

    mainViewModel.getDetailRecipe(recipeid)

    mainViewModel.detailRecipe.observe(lifecycleOwner, Observer{
            response ->
        if (response.id.isNotEmpty()) {
            recipe_name.value = response.name
            detailrecipeloading.value = false
        }else{
            Log.d("The Detail Recipe is Empty", detailrecipe.toString())
        }


    })

    LaunchedEffect(key1 = true) {
        mainViewModel.getDetailRecipe(recipeid)
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
//                .verticalScroll(rememberScrollState())
        ) {
            item {
                Text("Test: ${recipe_name.value}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
//            items(detailrecipe) { recipe ->
//                DetailRecipeItem(recipe = recipe)
//            }


            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

//@Composable
//fun DetailRecipeItem(recipe: Recipes) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .shadow(4.dp)
////            .clickable(onClick = onClick),
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            Text(
//                text = recipe.id,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = "Servings: ${recipe.servings}",
//                fontSize = 14.sp
//            )
//            Text(
//                text = "Time needed: ${recipe.making_time_in_minutes} minutes",
//                fontSize = 14.sp
//            )
//        }
//    }
//}