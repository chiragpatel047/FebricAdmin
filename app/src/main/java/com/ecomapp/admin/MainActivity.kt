package com.ecomapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ecomapp.admin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.hide()

        binding.banners.setOnClickListener {

        }

        binding.categories.setOnClickListener {
            val intent = Intent(this,ParentCat::class.java)
            startActivity(intent)
        }

        binding.products.setOnClickListener {

        }
    }
}