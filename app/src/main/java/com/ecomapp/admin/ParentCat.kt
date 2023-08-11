package com.ecomapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomapp.admin.Adapters.ParentCatAdapter
import com.ecomapp.admin.Factories.ParentCatVMF
import com.ecomapp.admin.Models.ParentCatModel
import com.ecomapp.admin.ViewModels.ParentCatViewModel
import com.ecomapp.admin.databinding.ActivityParentCatBinding
import com.ecomapp.febric.Repositories.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParentCat : AppCompatActivity() {

    lateinit var binding: ActivityParentCatBinding

    lateinit var parentCatList : ArrayList<ParentCatModel>
    lateinit var parentCatAdapter: ParentCatAdapter

    lateinit var parentCatViewModel: ParentCatViewModel

    @Inject
    lateinit var parentCatVMF: ParentCatVMF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_parent_cat)
        supportActionBar?.hide()

        binding.parentCatRecv.layoutManager = LinearLayoutManager(this)

        parentCatViewModel =ViewModelProvider(this,parentCatVMF).get(ParentCatViewModel::class.java)
        parentCatViewModel.LoadParentCateories()

        parentCatList = ArrayList()
        parentCatAdapter = ParentCatAdapter(this,parentCatList)
        binding.parentCatRecv.adapter = parentCatAdapter

        binding.addMainCat.setOnClickListener {
            val intent = Intent(this,AddSubCategory::class.java)
            startActivity(intent)
        }

        parentCatViewModel.parentCategories.observe(this,{
            when(it){
                is Response.Sucess -> {
                    parentCatList.clear()
                    parentCatList.addAll(it.data!!)
                    parentCatAdapter.notifyDataSetChanged()
                    it.data.clear()
                }
                is Response.Error -> {

                }
                is Response.Loading -> {

                }

            }
        })

    }

    override fun onResume() {
        super.onResume()
    }

}