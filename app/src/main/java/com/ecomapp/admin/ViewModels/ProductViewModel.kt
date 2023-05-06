package com.ecomapp.admin.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.Repositories.DataRepository
import com.ecomapp.febric.Models.ProuctModel
import com.ecomapp.febric.Repositories.Response
import kotlinx.coroutines.async
import javax.inject.Inject

class ProductViewModel @Inject constructor(val dataRepository: DataRepository) : ViewModel() {

    var product_mutableLiveData = MutableLiveData<Response<ArrayList<ProuctModel>>>()
    val product_liveData : LiveData<Response<ArrayList<ProuctModel>>>
        get() = product_mutableLiveData

    var delete_mutableLiveData = MutableLiveData<Response<String>>()
    val delete_liveData : LiveData<Response<String>>
        get() = delete_mutableLiveData

    fun LoadAllProducts(){
        viewModelScope.async {
            val result = dataRepository.LoadAllProducts()
            product_mutableLiveData.postValue(result)
        }
    }

    fun deleteProduct(productId : String){
        viewModelScope.async {
            val result = dataRepository.deleteProduct(productId)
            delete_mutableLiveData.postValue(result)
        }
    }
}