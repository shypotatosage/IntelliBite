package com.imtuc.intellibite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imtuc.intellibite.model.Nutrition_Profiles
import com.imtuc.intellibite.repository.ItemRepository
import javax.inject.Inject

class NutritionProfilesViewModel @Inject constructor(
    private val repo: ItemRepository
) : ViewModel(){
    val _ownedNutritionProfiles: MutableLiveData<List<Nutrition_Profiles>> by lazy {
        MutableLiveData<List<Nutrition_Profiles>>()
    }

    val ownedNutritionProfiles: LiveData<List<Nutrition_Profiles>>
        get() = _ownedNutritionProfiles

}