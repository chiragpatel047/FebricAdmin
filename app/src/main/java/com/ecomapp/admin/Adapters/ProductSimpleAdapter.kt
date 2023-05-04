package com.ecomapp.febric.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecomapp.admin.R
import com.ecomapp.admin.databinding.ProductSimpleSingleItemBinding
import com.ecomapp.febric.Models.ProuctModel

class ProductSimpleAdapter(myContext: Context, myItemModel: ArrayList<ProuctModel>) : RecyclerView.Adapter<ProductSimpleAdapter.ProductSimpleViewHolder>() {

    var context = myContext
    var productList = myItemModel

    class ProductSimpleViewHolder(productSimpleItemBinding: ProductSimpleSingleItemBinding) :
        RecyclerView.ViewHolder(productSimpleItemBinding.getRoot()) {

        var productSimpleItemBinding: ProductSimpleSingleItemBinding

        init {
            this.productSimpleItemBinding = productSimpleItemBinding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSimpleViewHolder {
        val binding: ProductSimpleSingleItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_simple_single_item, parent, false
        )

        return ProductSimpleViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductSimpleViewHolder, position: Int) {

        var singleItem = productList.get(position)

        Glide.with(context).load(singleItem.productMainImage).into(holder.productSimpleItemBinding.itemImage)
        holder.productSimpleItemBinding.itemText.text = singleItem.productTitle
        holder.productSimpleItemBinding.itemName.text = singleItem.productSubTitle
        holder.productSimpleItemBinding.itemRating.rating = singleItem.rate?.toFloatOrNull()!!
        holder.productSimpleItemBinding.itemNumberOfRating.text = "("+singleItem.noOfRating+")"
        holder.productSimpleItemBinding.itemOldPrice.text = singleItem.productOldPrice+"₹"
        holder.productSimpleItemBinding.itemNewPrice.text = singleItem.productPrice+"₹"

//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, FullProduct::class.java)
//            intent.putExtra("productId",singleItem.ProductId)
//            context.startActivity(intent)
//        }
    }
}