package com.ecomapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecomapp.admin.Factories.MainCatVMF
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.ViewModels.MainCatViewModel
import com.ecomapp.admin.databinding.ActivityMainCatBinding
import com.ecomapp.febric.Adapters.MainCatAdapter
import com.ecomapp.febric.Repositories.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainCat : AppCompatActivity() {

    lateinit var binding: ActivityMainCatBinding

    lateinit var mainCatViewModel: MainCatViewModel

    lateinit var mainCatList : ArrayList<MainCatModel>
    lateinit var mainCatAdapter: MainCatAdapter

    @Inject
    lateinit var mainCatVMF: MainCatVMF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_cat)
        supportActionBar?.hide()

        val parentCat = intent.getStringExtra("parentCat")
        binding.topName.text = parentCat

        mainCatViewModel = ViewModelProvider(this,mainCatVMF).get(MainCatViewModel::class.java)

        mainCatList = ArrayList()
        mainCatAdapter = MainCatAdapter(this,mainCatList)

        binding.catRecv.layoutManager = LinearLayoutManager(this)
        binding.catRecv.adapter = mainCatAdapter

        mainCatViewModel.LoadMainCategories(parentCat!!)

        mainCatViewModel.mainCat_liveData.observe(this,{
            when(it){
                is Response.Sucess ->{
                    mainCatList.clear()
                    mainCatList.addAll(it.data!!)
                    it.data.clear()

                    mainCatAdapter.notifyDataSetChanged()

                }

                is Response.Error ->{

                }
                is Response.Loading -> {

                }
            }
        })

        mainCatAdapter.notifyDataSetChanged()

    }
}