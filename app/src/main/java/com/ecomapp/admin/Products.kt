package com.ecomapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomapp.admin.Factories.ProductVMF
import com.ecomapp.admin.ViewModels.ProductViewModel
import com.ecomapp.admin.databinding.ActivityProductsBinding
import com.ecomapp.febric.Adapters.ProductSimpleAdapter
import com.ecomapp.febric.Models.ProuctModel
import com.ecomapp.febric.Repositories.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint()
class Products : AppCompatActivity() {

    lateinit var binding : ActivityProductsBinding

    lateinit var productViewModel: ProductViewModel

    @Inject
    lateinit var productVMF: ProductVMF

    lateinit var productList : ArrayList<ProuctModel>
    lateinit var productAdapter : ProductSimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_products)
        supportActionBar?.hide()

        productViewModel = ViewModelProvider(this,productVMF).get(ProductViewModel::class.java)

        productViewModel.LoadAllProducts()

        productList = ArrayList()
        productAdapter = ProductSimpleAdapter(this,productList,::deleteItem)

        binding.productsRecv.layoutManager = LinearLayoutManager(this)
        binding.productsRecv.adapter = productAdapter

        binding.addProduct.setOnClickListener {
            val intent = Intent(this,AddProduct::class.java)
            startActivity(intent)
        }

        productViewModel.product_liveData.observe(this,{

            when(it){
                is Response.Sucess -> {
                    productList.clear()

                    for(singleitem in it.data!!){
                        productList.add(0,singleitem)
                    }
                    
                    it.data.clear()
                    productAdapter.notifyDataSetChanged()
                }

                is Response.Error -> {

                }
                is Response.Loading ->{

                }
            }
        })
    }

    fun deleteItem(productId : String) {
        productViewModel.deleteProduct(productId)
    }
}