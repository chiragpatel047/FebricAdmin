package com.ecomapp.febric.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecomapp.admin.databinding.DeliveryItemSingleBinding
import com.ecomapp.febric.Models.OrderModel
import com.ecomapp.admin.R

class DeliveryItemAdapter(
    myContext: Context,
    myDeliveryItemModel: ArrayList<OrderModel>,
    val deliveredOrderFun: (String, String) -> Unit,
    val cancelOrderFun: (String, String) -> Unit

) : RecyclerView.Adapter<DeliveryItemAdapter.DeliveryItemViewHolder>() {

    var context = myContext
    var deliveryItemArrayList = myDeliveryItemModel

    class DeliveryItemViewHolder(devliveryItemBinding: DeliveryItemSingleBinding) :
        RecyclerView.ViewHolder(devliveryItemBinding.getRoot()) {

        var devliveryItemBinding: DeliveryItemSingleBinding
        init {
            this.devliveryItemBinding = devliveryItemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryItemViewHolder {

        val binding: DeliveryItemSingleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.delivery_item_single, parent, false
        )
        return DeliveryItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return deliveryItemArrayList.size
    }

    override fun onBindViewHolder(holder: DeliveryItemViewHolder, position: Int) {

        var singleItem = deliveryItemArrayList.get(position)

        holder.devliveryItemBinding.orderno.text = "Order no : "+singleItem.orderId?.substring(6,14)
        holder.devliveryItemBinding.trackingno.text = singleItem.orderId
        holder.devliveryItemBinding.amount.text = singleItem.productPrice+"₹"
        holder.devliveryItemBinding.quantity.text = "1"

        holder.devliveryItemBinding.delivered.setOnClickListener {
            deliveryItemArrayList.remove(singleItem)
            notifyDataSetChanged()
            deliveredOrderFun.invoke(singleItem.orderId!!, singleItem.userId!!)
        }

        holder.devliveryItemBinding.cancel.setOnClickListener {
            deliveryItemArrayList.remove(singleItem)
            notifyDataSetChanged()
            cancelOrderFun.invoke(singleItem.orderId!!, singleItem.userId!!)
        }

        holder.itemView.setOnClickListener {

//            val bottomSheetView : DetailsBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.details_bottom_sheet,null,false)
//
//            var bottomSheet = BottomSheetDialog(context,R.style.BottomSheetDialogTheme)
//            bottomSheet.setContentView(bottomSheetView.root)
//            bottomSheet.create()
//
//            bottomSheet.show()

        }
    }

}