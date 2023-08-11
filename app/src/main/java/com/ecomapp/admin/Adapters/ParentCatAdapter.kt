package com.ecomapp.admin.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecomapp.admin.MainCat
import com.ecomapp.admin.Models.ParentCatModel
import com.ecomapp.admin.R
import com.ecomapp.admin.databinding.ParentCatSingleBinding
import com.ecomapp.admin.databinding.SubCategorySingleBinding

class ParentCatAdapter(myContext : Context, myList : ArrayList<ParentCatModel>) : RecyclerView.Adapter<ParentCatAdapter.ParentCatViewHolder>() {

    val context = myContext
    val parentCatList = myList

    class ParentCatViewHolder(parentCatBinding: ParentCatSingleBinding) :
        RecyclerView.ViewHolder(parentCatBinding.getRoot()) {

        var parentCatBinding: ParentCatSingleBinding

        init {
            this.parentCatBinding = parentCatBinding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentCatViewHolder {
        val binding: ParentCatSingleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.parent_cat_single, parent, false
        )

        return ParentCatAdapter.ParentCatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return parentCatList.size
    }

    override fun onBindViewHolder(holder: ParentCatViewHolder, position: Int) {

        val singleItem = parentCatList.get(position)
        holder.parentCatBinding.parentCat.text = singleItem.parentCatName.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MainCat::class.java)
            intent.putExtra("parentCat",singleItem.parentCatName)
            context.startActivity(intent)
        }

    }

}