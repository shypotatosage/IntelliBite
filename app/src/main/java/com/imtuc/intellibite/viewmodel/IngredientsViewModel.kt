package com.imtuc.intellibite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imtuc.intellibite.model.Ingredients
import com.imtuc.intellibite.repository.ItemRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class IngredientsViewModel @Inject constructor(
    private val repo: ItemRepository
) : ViewModel(){
    val _ownedIngredients: MutableLiveData<List<Ingredients>> by lazy {
        MutableLiveData<List<Ingredients>>()
    }

    val ownedIngredients: LiveData<List<Ingredients>>
        get() = _ownedIngredients


}