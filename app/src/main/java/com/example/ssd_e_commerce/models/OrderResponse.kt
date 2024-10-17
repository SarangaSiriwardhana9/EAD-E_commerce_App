package com.example.ssd_e_commerce.models

data class OrderResponse(
    val message: String,
    val data: OrderData
)

data class OrderData(
    val id: String,
    val customerId: String,
    val totalAmount: Double,
    val status: String,
    val orderItems: List<OrderItem>,
    val createdAt: String,
    val updatedAt: String
)

data class OrderItem(
    val productId: String,
    val quantity: Int,
    val priceAtPurchase: Double
)