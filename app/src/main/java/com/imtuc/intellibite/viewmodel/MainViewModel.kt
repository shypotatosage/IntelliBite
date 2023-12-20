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
                            var recipe_id = item.asJsonObject["id"].asString
                            var recipe_name = item.asJsonObject["name"].asString
                            var recipe_making_time =
                                item.asJsonObject["making_time_in_minutes"].asInt
                            var recipe_servings = item.asJsonObject["servings"].asInt
                            var recipe_calories = item.asJsonObject["calories_per_portion"].asInt
                            var recipe_fats = item.asJsonObject["fats_per_portion"].asInt
                            var recipe_proteins = item.asJsonObject["fats_per_portion"].asInt
                            var recipe_carbs = item.asJsonObject["proteins_per_portion"].asInt

                            var ingredientsList = arrayListOf<Recipe_Ingredients>()

                            var nutritionsList = arrayListOf<Nutrition_Profiles>()

                            var stepsList = arrayListOf<Steps>()

                            var recipe = Recipes(
                                recipe_id,
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

                    var recipe_id = item.asJsonObject["id"].asString
                    var recipe_name = item.asJsonObject["name"].asString
                    var recipe_making_time = item.asJsonObject["making_time_in_minutes"].asInt
                    var recipe_servings = item.asJsonObject["servings"].asInt
                    var recipe_calories = item.asJsonObject["calories_per_portion"].asInt
                    var recipe_fats = item.asJsonObject["fats_per_portion"].asInt
                    var recipe_proteins = item.asJsonObject["proteins_per_portion"].asInt
                    var recipe_carbs = item.asJsonObject["carbs_per_portion"].asInt

                    var ingredientsList = arrayListOf<Recipe_Ingredients>()
                    ingredientsList.clear()

                    if (!item.get("ingredients").isJsonNull) {
                        var ingredients = item.getAsJsonArray("ingredients")

                        for (item in ingredients) {
                            var ingredient_id = item.asJsonObject["id"].asString
                            var ingredient_quantity = item.asJsonObject["quantity"].asInt
                            var ingredient_description = item.asJsonObject["description"].asString
                            var ingredient_description_step: String? = item.asJsonObject["description_steps"]?.asString
                            var ingredient_name = item.asJsonObject["name"].asString
                            var ingredient_unit = item.asJsonObject["unit"].asString
                            var ingredient_recipe_id = item.asJsonObject["recipe_id"].asInt
                            var ingredient_ingredient_id = item.asJsonObject["ingredient_id"].asInt


                            var ingredient = Recipe_Ingredients(
                                ingredient_id,
                                ingredient_quantity,
                                ingredient_description,
                                ingredient_description_step,
                                ingredient_unit,
                                ingredient_recipe_id,
                                ingredient_ingredient_id,
                                ingredient_name,
                            )

                            ingredientsList.add(ingredient)
                        }
                    }

                    var nutritionsList = arrayListOf<Nutrition_Profiles>()
                    nutritionsList.clear()

                    if (!item.get("nutrition_profiles").isJsonNull) {
                        var nutritions = item.getAsJsonArray("nutrition_profiles")

                        for (item in nutritions) {
                            var nutrition_id = item.asJsonObject["id"].asString
                            var nutrition_name = item.asJsonObject["name"].asString


                            var nutrition = Nutrition_Profiles(
                                nutrition_id,
                                nutrition_name,
                            )

                            nutritionsList.add(nutrition)
                        }
                    }
                    var stepsList = arrayListOf<Steps>()
                    stepsList.clear()

                    if (!item.get("steps").isJsonNull) {
                        var steps = item.getAsJsonArray("steps")

                        for (item in steps) {
                            var step_id = item.asJsonObject["id"].asString
                            var step_description = item.asJsonObject["description"].asString


                            var steps = Steps(
                                step_id,
                                step_description,
                            )

                            stepsList.add(steps)
                        }
                    }

                    _detailRecipe.value = Recipes(
                        recipe_id,
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

                        var name = data.asJsonObject["name"].asString
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

                        var fruitVegetables = FruitVegetables(name, servings_in_grams, calories, total_fat, saturated_fat, trans_fat, polyunsaturated_fat, monounsaturated_fat, carbohydrate, protein, fiber, sugar, cholesterol, sodium, vitamind, calcium, iron, potassium, caffeine)

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
                            var ingredient_id = item.asJsonObject["id"].asString
                            var ingredient_name = item.asJsonObject["name"].asString


                            var ingredient = Ingredients(
                                ingredient_id,
                                ingredient_name,
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
                            var nutrition_id = item.asJsonObject["id"].asString
                            var nutrition_name = item.asJsonObject["name"].asString


                            var nutritionProfile = Nutrition_Profiles(
                                nutrition_id,
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