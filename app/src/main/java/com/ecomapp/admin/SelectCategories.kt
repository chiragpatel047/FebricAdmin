package com.ecomapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ecomapp.admin.Factories.SelectCatVMF
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.ViewModels.SelectCateViewModel
import com.ecomapp.admin.databinding.ActivitySelectCategoriesBinding
import com.ecomapp.febric.Repositories.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint()
class SelectCategories : AppCompatActivity() {

    lateinit var binding : ActivitySelectCategoriesBinding

    lateinit var selectCateViewModel: SelectCateViewModel

    @Inject
    lateinit var selectCatVMF: SelectCatVMF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_categories)
        supportActionBar?.hide()

        selectCateViewModel = ViewModelProvider(this,selectCatVMF).get(SelectCateViewModel::class.java)

        val ParentCatSpinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1)

        ParentCatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.parentCatSpinner.adapter = ParentCatSpinnerAdapter
        ParentCatSpinnerAdapter.add("Select category")
        ParentCatSpinnerAdapter.add("Mens")
        ParentCatSpinnerAdapter.add("Womens")
        ParentCatSpinnerAdapter.add("Kids")
        ParentCatSpinnerAdapter.notifyDataSetChanged()

        val MainCatSpinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1)

        ParentCatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.subCatSpinner.adapter = MainCatSpinnerAdapter
        MainCatSpinnerAdapter.add("Select sub category")
        MainCatSpinnerAdapter.notifyDataSetChanged()

        selectCateViewModel.mainCat_liveData.observe(this,{
            when(it){
                is Response.Sucess -> {

                    MainCatSpinnerAdapter.clear()
                    MainCatSpinnerAdapter.add("Select sub category")

                    for(singleItem in it.data!!){
                        MainCatSpinnerAdapter.add(singleItem.mainCatName)
                    }
                    MainCatSpinnerAdapter.notifyDataSetChanged()
                    it.data.clear()
                }
                is Response.Error -> {

                }
                is Response.Loading -> {

                }

            }
        })

        binding.parentCatSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                MainCatSpinnerAdapter.clear()
                MainCatSpinnerAdapter.add("Select sub category")
                MainCatSpinnerAdapter.notifyDataSetChanged()
                val selectedBranch = binding.parentCatSpinner.selectedItem.toString()
                selectCateViewModel.LoadMainCategories(selectedBranch)
            }
        }

    }
}