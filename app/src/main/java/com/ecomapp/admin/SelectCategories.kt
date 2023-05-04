package com.ecomapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomapp.admin.Factories.SelectCatVMF
import com.ecomapp.admin.Models.ProductImageModel
import com.ecomapp.admin.Models.SubCatModel
import com.ecomapp.admin.ViewModels.SelectCateViewModel
import com.ecomapp.admin.databinding.ActivitySelectCategoriesBinding
import com.ecomapp.febric.Models.ProuctModel
import com.ecomapp.febric.Models.SizeModel
import com.ecomapp.febric.Repositories.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint()
class SelectCategories : AppCompatActivity() {

    lateinit var binding : ActivitySelectCategoriesBinding
    lateinit var selectCateViewModel: SelectCateViewModel

    @Inject
    lateinit var selectCatVMF: SelectCatVMF

    var selectedParentCat  : String = ""
    var selectedMainCat  : String = ""
    var selectedSubCat  : String = ""

    lateinit var subCatList : ArrayList<SubCatModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_categories)
        supportActionBar?.hide()

        selectCateViewModel = ViewModelProvider(this,selectCatVMF).get(SelectCateViewModel::class.java)

        selectCateViewModel.LoadHomeBanners()
        selectCateViewModel.bannerData_liveData.observe(this,{

            when(it){
                is Response.Sucess ->{
                    binding.checkboxTop.text = it.data?.get(0)?.MainTitle.toString()
                    binding.checkboxTop2.text = it.data?.get(1)?.MainTitle.toString()
                    binding.checkboxTop3.text = it.data?.get(2)?.MainTitle.toString()
                    binding.checkboxTop4.text = it.data?.get(3)?.MainTitle.toString()
                    binding.checkboxTop5.text = it.data?.get(4)?.MainTitle.toString()
                    binding.checkboxTop5.text = it.data?.get(5)?.MainTitle.toString()
                }

                is Response.Error -> {

                }
                is Response.Loading -> {

                }
            }
        })

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

        val SubCatSpinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1)

        SubCatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.subCatSpinner2.adapter = SubCatSpinnerAdapter
        SubCatSpinnerAdapter.add("Select sub category")
        SubCatSpinnerAdapter.notifyDataSetChanged()

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

        selectCateViewModel.subCat_liveData.observe(this,{
            when(it){
                is Response.Sucess ->{
                    SubCatSpinnerAdapter.clear()
                    SubCatSpinnerAdapter.add("Select sub category")

                    for(singleItem in it.data!!){
                        SubCatSpinnerAdapter.add(singleItem.subCatName)
                    }
                    SubCatSpinnerAdapter.notifyDataSetChanged()
                    it.data.clear()
                }
                is Response.Error ->{

                }
                is Response.Loading ->{

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
                selectedParentCat = binding.parentCatSpinner.selectedItem.toString()
                selectCateViewModel.LoadMainCategories(selectedParentCat)

            }
        }
        binding.subCatSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                selectedMainCat = binding.subCatSpinner.selectedItem.toString()
                selectCateViewModel.LoadSubCatigories(selectedParentCat,selectedMainCat)
            }
        }

        binding.subCatSpinner2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                selectedSubCat = binding.subCatSpinner2.selectedItem.toString()
            }
        }

        selectCateViewModel.add_liveData.observe(this,{
            when(it){
                is Response.Sucess -> {
                    Toast.makeText(this,"Added",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
                is Response.Error -> {
                    Toast.makeText(this,it.errorMsg.toString(),Toast.LENGTH_LONG).show()
                }
                is Response.Loading -> {

                }
            }
        })

        binding.btnAdd.setOnClickListener {

            val ProductId : String = System.currentTimeMillis().toString()
            val ProductTitle : String = AddProduct.productTitle
            val ProductSubTitle : String = AddProduct.productSubTitle
            val ProductDesc : String = AddProduct.productDesc
            val ProductOldPrice : String = AddProduct.productOldPrice
            val ProductPrice : String = AddProduct.productPrice
            val NoOfRating : String = "0"
            val Rate : String = "0.0F"

            val imageStringList : ArrayList<String> = AddProductImages.addImageArrayList
            val imageList : ArrayList<ProductImageModel> = ArrayList()

            for(singleImage in imageStringList){
                imageList.add(ProductImageModel(singleImage))
            }

            val ProductMainImage : String = imageStringList.get(0)

            val prouctModel : ProuctModel = ProuctModel(ProductId,
                ProductTitle,
                ProductSubTitle,
                ProductDesc,
                ProductOldPrice,
                ProductPrice,
                ProductMainImage,
                NoOfRating,
                Rate)

            val sizeList : ArrayList<SizeModel> = SelectSize.sizeList

            selectCateViewModel.AddNewProduct(selectedParentCat,selectedMainCat,selectedSubCat,prouctModel,sizeList,imageList)

        }
    }
}