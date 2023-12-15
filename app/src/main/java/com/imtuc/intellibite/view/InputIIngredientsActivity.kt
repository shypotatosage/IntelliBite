import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.imtuc.intellibite.R
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.navigation.Screen
import com.imtuc.intellibite.ui.theme.IntelliBiteTheme
import com.imtuc.intellibite.viewmodel.MainViewModel


@Composable
fun InputIngredientsActivity(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        mainViewModel.getIngredients()
    }

//    val availableIngredients = listOf("Carrots", "Broccoli", "Potatoes", "Chilis", "Tomatoes", "Onions")

    var ownedIngredients = remember {
        mutableStateListOf<String>()
    }

    var showResult = remember{
        mutableStateOf("")
    }

    var availableIngredients = remember {
        mutableStateListOf<Ingredients>()
    }

    var ingredientsLoading = remember {
        mutableStateOf(true)
    }


    mainViewModel.ingredients.observe(lifecycleOwner, Observer{
            response ->
        if (mainViewModel.ingredientsError.value == "Get Data Successful"){
            availableIngredients.clear()
            availableIngredients.addAll(mainViewModel.ingredients.value!!)
            ingredientsLoading.value = false
        }else{
            ingredientsLoading.value = true
            Toast.makeText(context, mainViewModel.ingredientsError.value, Toast.LENGTH_SHORT).show()
        }
//        if (response != null) {
//            showResult.value = mainViewModel.ingredients.value.toString()
//
//            navController.popBackStack()
//            navController.navigate(
//                Screen.InputNutritionProfiles.passParam(
//                    ownedIngredients.toString()
//                )
//            )
//
////            ingredientsViewModel.resetPrediction()
//        }
    })

//    val (checkedState, onStateChange) = remember { mutableStateOf(false) }

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
                Text("Choose Your Ingredients")
            }
            items(availableIngredients) { ingredient ->
                IngredientCheckbox(
                    ingredient = availableIngredients,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            ownedIngredients.add(ingredient.id)
                        } else {
                            ownedIngredients.remove(ingredient.id)
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        navController.navigate(Screen.InputNutritionProfiles.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Next", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun IngredientCheckbox(ingredient: List<Ingredients>, onCheckedChange: (Boolean) -> Unit) {
    val (checkedState, onStateChange) = remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .toggleable(
                value = checkedState,
                onValueChange = {
                    onStateChange(!checkedState)
                    onCheckedChange(!checkedState)
                },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = null,
//            colors = CheckboxDefaults.colors(checkedColor = Color.Green) // Set checkbox color to green
        )
        Text(
            text = ingredient.toString(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    IntelliBiteTheme {
//        InputIngredientsActivity(rememberNavController())
//    }
//}