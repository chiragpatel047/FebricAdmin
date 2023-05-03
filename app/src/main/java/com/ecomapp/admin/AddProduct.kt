package com.ecomapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ecomapp.admin.databinding.ActivityAddProductBinding

class AddProduct : AppCompatActivity() {

    lateinit var binding : ActivityAddProductBinding

    companion object{

        var productTitle : String = ""
        var productSubTitle : String = ""
        var productDesc : String = ""
        var productOldPrice : String = ""
        var productPrice : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_product)
        supportActionBar?.hide()

        binding.btnNext.setOnClickListener {

            val productTitle = binding.productTitle.editText?.text.toString()
            val productSubTitle = binding.productSubtitle.editText?.text.toString()
            val productDesc = binding.productDesc.editText?.text.toString()
            val productOldPrice = binding.productOldprice.editText?.text.toString()
            val productPrice = binding.productPrice.editText?.text.toString()

            if(productTitle.isEmpty() ||
                productSubTitle.isEmpty() ||
                productDesc.isEmpty() ||
                productOldPrice.isEmpty() ||
                productPrice.isEmpty()){

                Toast.makeText(this,"Please filled all details",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(this,AddProductImages::class.java)
            startActivity(intent)
        }
    }
}