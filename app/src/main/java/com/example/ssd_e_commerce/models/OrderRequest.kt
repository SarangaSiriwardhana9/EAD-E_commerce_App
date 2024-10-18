package com.example.ssd_e_commerce.models

import java.io.Serializable

data class OrderRequest(
    val id: String? = null,
    val customerId: String,
    val totalAmount: Double,
    val status: String,
    val orderItems: List<OrderItem>,
    val createdAt: String,
    val updatedAt: String
) : Serializable {
    data class OrderItem(
        val productId: String,
        val quantity: Int,
        val priceAtPurchase: Double,
        val vendorId: String
    ) : Serializable
}