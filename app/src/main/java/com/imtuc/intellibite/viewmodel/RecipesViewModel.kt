package com.imtuc.intellibite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imtuc.intellibite.repository.ItemRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    private val repo: ItemRepository
): ViewModel() {
    val _recipes: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val recipes: LiveData<String>
        get() = _recipes
    fun getRecipes(
        ingredients: List<String>,
        nutritionProfiles: List<String>,
    ) = viewModelScope.launch{
        repo.getRecipes(ingredients, nutritionProfiles).let {
            response ->
            if (response.isSuccessful) {
                _recipes.value = response.body()?.get("result")!!.asString
                Log.d("Recipes Result", response.body().toString())
            } else {
                _recipes.value = response.message()
                Log.e("Recipes Result", response.message())
            }
        }
    }
}