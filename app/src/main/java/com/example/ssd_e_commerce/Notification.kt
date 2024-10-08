package com.example.ssd_e_commerce

import com.example.ssd_e_commerce.ProductDetail.Product

data class Notification(
    val orderId: String,
    val product: Product,
    val quantity: Int,
    val status: String,
    val timestamp: Long,
)
