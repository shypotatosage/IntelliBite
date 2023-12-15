package com.imtuc.intellibite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.model.Recipes
import com.imtuc.intellibite.model.Steps
import com.imtuc.intellibite.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ItemRepository
) : ViewModel(){
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

    fun getRecipes(ingredients: List<Ingredients>, nutritionProfiles: List<Nutrition_Profiles>) = viewModelScope.launch {
        repo.getRecipes(ingredients, nutritionProfiles).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    _recipeError.value = "Get Data Successful"
                    _recipes.value = arrayListOf()

                    var tmpArrList = arrayListOf<Recipes>()

                    if (!response.body()!!.get("data").isJsonNull) {
                        val arr: JsonArray = response.body()!!.getAsJsonArray("data")


                        for (item in arr) {
                            var recipe_id = item.asJsonObject["id"].asString
                            var recipe_name = item.asJsonObject["name"].asString
                            var recipe_making_time = item.asJsonObject["making_time_in_minutes"].asInt
                            var recipe_servings = item.asJsonObject["servings"].asInt
                            var recipe_calories = item.asJsonObject["calories_per_portion"].asInt
                            var recipe_fats = item.asJsonObject["fats_per_portion"].asInt
                            var recipe_proteins = item.asJsonObject["fats_per_portion"].asInt
                            var recipe_carbs = item.asJsonObject["proteins_per_portion"].asInt
                            var ingredientsJson = item.asJsonObject["ingredients"].asString
                            var nutritionsJson = item.asJsonObject["nutritions"].asString
                            var stepsJson = item.asJsonObject["steps"].asString

                            val ingredientsList: List<Ingredients> =
                                Gson().fromJson(ingredientsJson, object : TypeToken<List<Ingredients>>() {}.type)

                            val nutritionsList: List<Nutrition_Profiles> =
                                Gson().fromJson(nutritionsJson, object : TypeToken<List<Nutrition_Profiles>>() {}.type)

                            val stepsList: List<Steps> =
                                Gson().fromJson(stepsJson, object : TypeToken<List<Steps>>() {}.type)

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

    val _detailrecipes: MutableLiveData<List<Recipes>> by lazy {
        MutableLiveData<List<Recipes>>()
    }

    val detailrecipes: LiveData<List<Recipes>>
        get() = _detailrecipes

    val _detailrecipeError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val detailrecipeError: LiveData<String>
        get() = _detailrecipeError
    fun getDetailRecipe(id: String) = viewModelScope.launch {
        repo.getDetailRecipe(id).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
                    if (!response.body()!!.get("data").isJsonNull) {
                        val data: JsonObject = response.body()!!.getAsJsonObject("data")

                        var recipe_id = data.asJsonObject["id"].asString
                        var recipe_name = data.asJsonObject["name"].asString
                        var recipe_making_time = data.asJsonObject["making_time_in_minutes"].asInt
                        var recipe_servings = data.asJsonObject["servings"].asInt
                        var recipe_calories = data.asJsonObject["calories_per_portion"].asInt
                        var recipe_fats = data.asJsonObject["fats_per_portion"].asInt
                        var recipe_proteins = data.asJsonObject["fats_per_portion"].asInt
                        var recipe_carbs = data.asJsonObject["proteins_per_portion"].asInt
                        var ingredientsJson = data.asJsonObject["ingredients"].asString
                        var nutritionsJson = data.asJsonObject["nutritions"].asString
                        var stepsJson = data.asJsonObject["steps"].asString

                        val ingredientsList: List<Ingredients> =
                            Gson().fromJson(ingredientsJson, object : TypeToken<List<Ingredients>>() {}.type)

                        val nutritionsList: List<Nutrition_Profiles> =
                            Gson().fromJson(nutritionsJson, object : TypeToken<List<Nutrition_Profiles>>() {}.type)

                        val stepsList: List<Steps> =
                            Gson().fromJson(stepsJson, object : TypeToken<List<Steps>>() {}.type)

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

                        var tmpArrList = arrayListOf<Recipes>()
                        tmpArrList.add(recipe)

                    }

                    _detailrecipeError.value = "Success"

                    Log.e("Detail Recipe Data", _detailrecipes.value.toString())
                } else {
                    _detailrecipeError.value = response.message()
                }

                Log.d("Get Detail Recipe Data", _detailrecipeError.value.toString())
            } else {
                Log.e("Get Detail Recipe Data Error", response.message())
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
                if (response.body()?.get("message")?.asString == "Success") {
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
        repo.getIngredients().let { response ->
            if (response.isSuccessful) {
                if (response.body()?.get("message")?.asString == "Success") {
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