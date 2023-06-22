package com.ecomapp.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ecomapp.admin.databinding.FragmentHomeBinding


class Home : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        binding.banners.setOnClickListener {
            val intent = Intent(activity,Banners::class.java)
            startActivity(intent)
        }

        binding.categories.setOnClickListener {
            val intent = Intent(activity,ParentCat::class.java)
            startActivity(intent)
        }

        binding.products.setOnClickListener {
            val intent = Intent(activity,Products::class.java)
            intent.putExtra("loadFor","all")
            startActivity(intent)
        }

        return binding.root
    }

}