package com.ecomapp.admin.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.Repositories.DataRepository
import com.ecomapp.febric.Repositories.Response
import kotlinx.coroutines.async
import javax.inject.Inject

class MainCatViewModel @Inject constructor(val dataRepository: DataRepository) : ViewModel() {
    var mainCat_mutableLiveData = MutableLiveData<Response<ArrayList<MainCatModel>>>()
    val mainCat_liveData : LiveData<Response<ArrayList<MainCatModel>>>
        get() = mainCat_mutableLiveData

    fun LoadMainCategories(catName : String){
        viewModelScope.async {
            val result = dataRepository.LoadMainCategories(catName)
            mainCat_mutableLiveData.postValue(result)
        }
    }

}