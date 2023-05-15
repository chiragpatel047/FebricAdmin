package com.ecomapp.febric.Models


data class OrderModel(

    val orderId : String? = null,
    val productId : String? = null,
    var userId : String? = null,
    val productPrice: String? = null,
    val selectedSize  : String? = null,
    val name : String? = null,
    val address: String? = null,
    val city : String? = null,
    val state : String? = null,
    val zipCode : String? = null,
    val phoneNO : String? = null

)
