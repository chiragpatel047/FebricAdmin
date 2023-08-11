package com.ecomapp.admin.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomapp.admin.Models.ParentCatModel
import com.ecomapp.admin.Repositories.DataRepository
import com.ecomapp.febric.Repositories.Response
import kotlinx.coroutines.async
import javax.inject.Inject

class ParentCatViewModel @Inject constructor(val dataRepository: DataRepository) : ViewModel() {

    var _parentCategories = MutableLiveData<Response<ArrayList<ParentCatModel>>>()
    val parentCategories : LiveData<Response<ArrayList<ParentCatModel>>>
        get() = _parentCategories

    var _addParentCategories = MutableLiveData<Response<String>>()
    val addParentCategories : LiveData<Response<String>>
        get() = _addParentCategories

    fun LoadParentCateories(){
        viewModelScope.async {
            val result = dataRepository.LoadParentCategories()
            _parentCategories.postValue(result)
        }
    }

    fun addParentCategory(parentCatName : String){
        viewModelScope.async {
            val result = dataRepository.addParentCategory(parentCatName)
            _addParentCategories.postValue(result)
        }
    }
}