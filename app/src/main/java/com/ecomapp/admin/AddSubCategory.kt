package com.ecomapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ecomapp.admin.Factories.AddMainCatVMF
import com.ecomapp.admin.Factories.AddSubCatVMF
import com.ecomapp.admin.Factories.ParentCatVMF
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.Models.SubCatModel
import com.ecomapp.admin.ViewModels.AddMainCatViewModel
import com.ecomapp.admin.ViewModels.AddSubCatViewModel
import com.ecomapp.admin.ViewModels.ParentCatViewModel
import com.ecomapp.admin.databinding.ActivityAddSubCategoryBinding
import com.ecomapp.febric.Repositories.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint()
class AddSubCategory : AppCompatActivity() {

    lateinit var binding : ActivityAddSubCategoryBinding
    lateinit var parentCatViewModel: ParentCatViewModel

    @Inject
    lateinit var parentCatVMF: ParentCatVMF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_sub_category)
        supportActionBar?.hide()

        parentCatViewModel = ViewModelProvider(this,parentCatVMF).get(ParentCatViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            val catName = binding.catName.editText?.text.toString()
            if(catName.isEmpty()){

                Toast.makeText(this,"Can't be empty",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            parentCatViewModel.addParentCategory(catName)

            parentCatViewModel.addParentCategories.observe(this,{
                when(it){
                    is Response.Sucess -> {
                        Toast.makeText(this,"Added successfully",Toast.LENGTH_LONG).show()
                        finish()
                    }

                    is Response.Error -> {
                        Toast.makeText(this,it.errorMsg.toString(),Toast.LENGTH_LONG).show()
                    }
                    is Response.Loading -> {

                    }
                }
            })
        }
    }
}