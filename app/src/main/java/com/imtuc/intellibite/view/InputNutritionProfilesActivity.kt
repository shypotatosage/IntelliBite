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
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.navigation.Screen
import com.imtuc.intellibite.viewmodel.MainViewModel


@Composable
fun InputNutritionProfilesActivity(
    ingredient: String,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    var showResult = remember{
        mutableStateOf("")
    }
    var availableNutritionProfile = remember {
        mutableStateListOf<Nutrition_Profiles>()
    }

    var ownedNutritionProfile by remember { mutableStateOf("") }

    var nutritionProfilessLoading = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true) {
        mainViewModel.getNutritionProfile()
    }

    mainViewModel.nutritionProfile.observe(lifecycleOwner, Observer{
            response ->
        if (mainViewModel.nutritionProfileError.value == "Get Data Successful"){
            availableNutritionProfile.clear()
            availableNutritionProfile.addAll(mainViewModel.nutritionProfile.value!!)
            nutritionProfilessLoading.value = false
        }else{
            nutritionProfilessLoading.value = true
            Toast.makeText(context, mainViewModel.nutritionProfileError.value, Toast.LENGTH_SHORT).show()
        }
    })

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
                Text("Choose Your Specific Conditions")
            }
//            items(availableNutritionProfile) { nutrition ->
//                DiseasesCheckbox(
//                    nutrition = nutrition,
//                    onCheckedChange = { isChecked ->
//                        if (isChecked) {
//                            ownedNutritionProfile.add(nutrition.id)
//                        } else {
//                            ownedNutritionProfile.remove(nutrition.id)
//                        }
//                    }
//                )
//            }


                items(availableNutritionProfile) { nutrition ->
                    DiseasesCheckbox(
                        nutrition = nutrition,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                ownedNutritionProfile += "${nutrition.id},"
                            } else {
                                ownedNutritionProfile = ownedNutritionProfile.replace("${nutrition.id},", "")
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
                        navController.navigate(Screen.Result.passParam(
                            ingredient,
                            ownedNutritionProfile
                        ))
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
fun DiseasesCheckbox(nutrition: Nutrition_Profiles, onCheckedChange: (Boolean) -> Unit) {
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
            text = nutrition.name,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

