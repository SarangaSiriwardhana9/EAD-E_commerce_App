package com.example.ssd_e_commerce.models

import java.util.Date

data class CartListResponse(
    val message: String,
    val data: List<Cart>
)

data class CartResponse(
    val message: String,
    val data: Cart
)

data class Cart(
    val id: String,
    val customerId: String,
    val items: List<CartItem>,
    val createdAt: Date,
    val updatedAt: Date
)

data class CartItem(
    val productId: String,
    val quantity: Int,
    val price: Double
)

data class CartRequest(
    val id: String? = null,
    val customerId: String,
    val items: List<CartItemRequest>,
    val createdAt: String = java.time.Instant.now().toString(),
    val updatedAt: String = java.time.Instant.now().toString()
)

data class CartItemRequest(
    val productId: String,
    val quantity: Int,
    val price: Double
)

data class UpdateCartRequest(
    val customerId: String,
    val items: List<CartItemRequest>,
    val updatedAt: String = java.time.Instant.now().toString()
)