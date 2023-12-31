package com.imtuc.intellibite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.imtuc.intellibite.model.FruitVegetables
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.model.Recipe_Ingredients
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.model.Steps
import com.imtuc.intellibite.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ItemRepository
) : ViewModel() {
    val _recipes: MutableLiveData<List<Recipes>> by lazy {
        MutableLiveData<List<Recipes>>()
    }

    val recipes: LiveData<List<Recipes>>
        get() = _recipes

    val _recipeError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val recipeError: LiveData<String>
        get() = _recipeError

    fun getRecipes(ingredients: String, nutritionProfiles: String) = viewModelScope.launch {
        repo.getRecipes(ingredients, nutritionProfiles).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "success") {
                    _recipeError.value = "Get Data Successful"
                    _recipes.value = arrayListOf()

                    var tmpArrList = arrayListOf<Recipes>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")

                        for (item in arr) {
                            var recipe_name = item.asJsonObject["recipe_name"].asString
                            var recipe_making_time =
                                item.asJsonObject["timetotal"].asInt
                            var recipe_servings = item.asJsonObject["servings"].asInt

                            var recipe = Recipes(
                                recipe_name,
                                recipe_making_time,
                                recipe_servings
                            )

                            tmpArrList.add(recipe)
                        }
                    }

                    _recipes.value = tmpArrList

                    Log.e("Recipes Data", _recipes.value.toString())
                } else {
                    _recipeError.value = response.message()
                }

                Log.d("Get Recipes Data", _recipeError.value.toString())
            } else {
                Log.e("Get Recipes Data Error", response.message())
            }
        }
    }

    val _detailRecipe: MutableLiveData<Recipes> by lazy {
        MutableLiveData<Recipes>()
    }

    val detailRecipe: LiveData<Recipes>
        get() = _detailRecipe

    val _detailrecipeError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val detailrecipeError: LiveData<String>
        get() = _detailrecipeError

    fun getDetailRecipe(id: String) = viewModelScope.launch {
        repo.getDetailRecipe(id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "success") {
                    var item = response.body()!!.getAsJsonObject("data")

                    var recipe_name = item.asJsonObject["recipe_name"].asString
                    var recipe_making_time = item.asJsonObject["timetotal"].asInt
                    var recipe_servings = item.asJsonObject["servings"].asInt
                    var recipe_calories = item.asJsonObject["calories"].asInt
                    var recipe_fats = item.asJsonObject["fat"].asInt
                    var recipe_proteins = item.asJsonObject["protein"].asInt
                    var recipe_carbs = item.asJsonObject["carbohydrate"].asInt

                    var ingredientsList = arrayListOf<Ingredients>()
                    ingredientsList.clear()

                    if (!item.get("ingredients").isJsonNull) {
                        var ingredients = item.asJsonObject["ingredients_detailed"].asString.split("$").toTypedArray()

                        for (ingredient_name in ingredients) {

                            var ingredient = Ingredients(
                                ingredient_name,
                            )

                            ingredientsList.add(ingredient)
                        }
                    }

                    var nutritionsList = arrayListOf<Nutrition_Profiles>()
                    nutritionsList.clear()

                    if (!item.get("nutrition_profiles").isJsonNull) {
                        var nutritions = item.asJsonObject["nutrition_profiles"].asString.split("$").toTypedArray()

                        for (nutrition_name in nutritions) {

                            var nutrition = Nutrition_Profiles(
                                nutrition_name,
                            )

                            nutritionsList.add(nutrition)
                        }
                    }
                    var stepsList = arrayListOf<Steps>()
                    stepsList.clear()

                    if (!item.get("steps").isJsonNull) {
                        var steps = item.asJsonObject["steps"].asString.split("$").toTypedArray()

                        for (step_description in steps) {

                            var steps = Steps(
                                step_description,
                            )

                            stepsList.add(steps)
                        }
                    }

                    _detailRecipe.value = Recipes(
                        recipe_name,
                        recipe_making_time,
                        recipe_servings,
                        recipe_calories,
                        recipe_fats,
                        recipe_proteins,
                        recipe_carbs,
                        ingredientsList,
                        nutritionsList,
                        stepsList
                    )
                }

                Log.d("Recipe Detail", response.body().toString())
            } else {
                Log.e("Recipe Detail Error", response.message())
            }
        }
    }

    val _fruitVegetables: MutableLiveData<FruitVegetables> by lazy {
        MutableLiveData<FruitVegetables>()
    }

    val fruitVegetables: LiveData<FruitVegetables>
        get() = _fruitVegetables

    val _fruitVegetablesError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val fruitVegetablesError: LiveData<String>
        get() = _fruitVegetablesError

    fun getFruitVegetables(name: String) = viewModelScope.launch {
        repo.getFruitVegetables(name).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "success") {
                    if (!response.body()!!.get("data").isJsonNull) {
                        val data: JsonObject = response.body()!!.getAsJsonObject("data")

                        var fruitVegetablesName = data.asJsonObject["name"].asString
                        var servings_in_grams = data.asJsonObject["servings_in_grams"].asInt
                        var calories = data.asJsonObject["calories"].asInt
                        var total_fat = data.asJsonObject["total_fat"].asDouble
                        var saturated_fat = data.asJsonObject["saturated_fat"].asDouble
                        var trans_fat = data.asJsonObject["trans_fat"].asDouble
                        var polyunsaturated_fat = data.asJsonObject["polyunsaturated_fat"].asDouble
                        var monounsaturated_fat = data.asJsonObject["monounsaturated_fat"].asDouble
                        var carbohydrate = data.asJsonObject["carbohydrate"].asDouble
                        var protein = data.asJsonObject["protein"].asDouble
                        var fiber = data.asJsonObject["fiber"].asDouble
                        var sugar = data.asJsonObject["sugar"].asDouble
                        var cholesterol = data.asJsonObject["cholesterol"].asDouble
                        var sodium = data.asJsonObject["sodium"].asDouble
                        var vitamind = data.asJsonObject["vitamind"].asDouble
                        var calcium = data.asJsonObject["calcium"].asDouble
                        var iron = data.asJsonObject["iron"].asDouble
                        var potassium = data.asJsonObject["potassium"].asDouble
                        var caffeine = data.asJsonObject["caffeine"].asDouble

                        var fruitVegetables = FruitVegetables(fruitVegetablesName, servings_in_grams, calories, total_fat, saturated_fat, trans_fat, polyunsaturated_fat, monounsaturated_fat, carbohydrate, protein, fiber, sugar, cholesterol, sodium, vitamind, calcium, iron, potassium, caffeine)

                        _fruitVegetables.value = fruitVegetables
                    }

                    _fruitVegetablesError.value = "Success"

                    Log.e("Fruit Vegetables Data", _fruitVegetables.value.toString())
                } else {
                    _fruitVegetablesError.value = response.message()
                }

                Log.d("Get Fruit Vegetables Data", _fruitVegetablesError.value.toString())
            } else {
                Log.e("Get Fruit Vegetables Data Error", response.message())
            }
        }
    }

    fun resetFruitVegetables() {
        _fruitVegetablesError.value = "reset"
    }

    fun clearFruitVegetables() {
        _fruitVegetablesError.value = ""
    }

    val _ingredients: MutableLiveData<List<Ingredients>> by lazy {
        MutableLiveData<List<Ingredients>>()
    }

    val ingredients: LiveData<List<Ingredients>>
        get() = _ingredients

    val _ingredientsError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val ingredientsError: LiveData<String>
        get() = _ingredientsError

    fun getIngredients() = viewModelScope.launch {
        repo.getIngredients().let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "success") {
                    _ingredientsError.value = "Get Data Successful"
                    _ingredients.value = arrayListOf()

                    var tmpArrList = arrayListOf<Ingredients>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")


                        for (item in arr) {
                            var ingredient_name = item.asString


                            var ingredient = Ingredients(
                                ingredient_name
                            )

                            tmpArrList.add(ingredient)
                        }
                    }

                    _ingredients.value = tmpArrList

                    Log.e("ingredient Data", _ingredients.value.toString())
                } else {
                    _ingredientsError.value = response.message()
                }

                Log.d("Get ingredient Data", _ingredientsError.value.toString())
            } else {
                Log.e("Get ingredient Data Error", response.message())
            }
        }
    }

    val _nutritionProfile: MutableLiveData<List<Nutrition_Profiles>> by lazy {
        MutableLiveData<List<Nutrition_Profiles>>()
    }

    val nutritionProfile: LiveData<List<Nutrition_Profiles>>
        get() = _nutritionProfile

    val _nutritionProfileError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val nutritionProfileError: LiveData<String>
        get() = _nutritionProfileError

    fun getNutritionProfile() = viewModelScope.launch {
        repo.getNutritionProfile().let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "success") {
                    _nutritionProfileError.value = "Get Data Successful"
                    _nutritionProfile.value = arrayListOf()

                    var tmpArrList = arrayListOf<Nutrition_Profiles>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")


                        for (item in arr) {
                            var nutrition_name = item.asString


                            var nutritionProfile = Nutrition_Profiles(
                                nutrition_name,
                            )

                            tmpArrList.add(nutritionProfile)
                        }
                    }

                    _nutritionProfile.value = tmpArrList

                    Log.e("nutritionProfile Data", _nutritionProfile.value.toString())
                } else {
                    _nutritionProfileError.value = response.message()
                }

                Log.d("Get nutritionProfile Data", _nutritionProfileError.value.toString())
            } else {
                Log.e("Get nutritionProfile Data Error", response.message())
            }
        }
    }

}