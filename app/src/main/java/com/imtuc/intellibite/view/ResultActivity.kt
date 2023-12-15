package com.imtuc.intellibite.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.viewmodel.MainViewModel

@Composable
fun ResultActivity(
    ingredient: List<Ingredients>,
    nutrition: List<Nutrition_Profiles>,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    var ownedRecipes by remember {
        mutableStateOf(emptyList<Recipes>())
    }

    // Observe changes in mainViewModel.recipes and update ownedRecipes
    LaunchedEffect(mainViewModel.recipes) {
        if (mainViewModel.recipeError.value == "Get Data Successful") {
            ownedRecipes = mainViewModel.recipes.value ?: emptyList()
        } else {
            Toast.makeText(context, mainViewModel.recipeError.value, Toast.LENGTH_SHORT).show()
        }
    }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        items(ownedRecipes) { recipe ->
//            RecipeItem(recipe = recipe)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    }
}

@Composable
fun RecipeItem(recipe: String) {
    Text(
        text = recipe,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
