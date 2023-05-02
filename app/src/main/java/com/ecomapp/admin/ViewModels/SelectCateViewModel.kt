package com.ecomapp.admin.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.Models.SubCatModel
import com.ecomapp.admin.Repositories.DataRepository
import com.ecomapp.febric.Repositories.Response
import kotlinx.coroutines.async
import javax.inject.Inject

class SelectCateViewModel @Inject constructor(val dataRepository: DataRepository) : ViewModel() {

    var mainCat_mutableLiveData = MutableLiveData<Response<ArrayList<MainCatModel>>>()
    val mainCat_liveData : LiveData<Response<ArrayList<MainCatModel>>>
        get() = mainCat_mutableLiveData

    var subCat_mutableLiveData = MutableLiveData<Response<ArrayList<SubCatModel>>>()
    val subCat_liveData: LiveData<Response<ArrayList<SubCatModel>>>
        get() = subCat_mutableLiveData

    fun LoadMainCategories(catName : String){
        viewModelScope.async {
            val result = dataRepository.LoadMainCategories(catName)
            mainCat_mutableLiveData.postValue(result)
        }
    }

    fun LoadSubCatigories(parentCatName: String, mainCatName: String) {
        viewModelScope.async {
            val result = dataRepository.LoadSubCatigories(parentCatName, mainCatName)
            subCat_mutableLiveData.postValue(result)
        }

    }

}