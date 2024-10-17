package com.example.ssd_e_commerce.models

import java.util.Date

data class Order(
    val id: String,
    val customerId: String,
    val totalAmount: Double,
    val status: String,
    val orderItems: List<OrderItem>,
    val createdAt: Date,
    val updatedAt: Date
)



data class OrderListResponse(
    val message: String,
    val data: List<Order>
)

data class DeleteOrderResponse(
    val message: String,
    val data: Order?
)