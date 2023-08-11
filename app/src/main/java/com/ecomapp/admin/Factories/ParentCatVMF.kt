package com.ecomapp.admin.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ecomapp.admin.Adapters.ParentCatAdapter
import com.ecomapp.admin.Repositories.DataRepository
import com.ecomapp.admin.ViewModels.ParentCatViewModel
import javax.inject.Inject

class ParentCatVMF @Inject constructor(val dataRepository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ParentCatViewModel(dataRepository) as T
    }
}