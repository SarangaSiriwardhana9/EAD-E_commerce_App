package com.example.ssd_e_commerce.models

import java.io.Serializable

data class ProductResponse(
    val message: String,
    val data: List<Product>
)

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val categoryId: String,
    val price: Double,
    val images: List<String>,
    val active: Boolean,
    val stockCount: Int,
    val vendorId: String,
    val createdAt: String,
    val updatedAt: String,
    val averageRating: Double,
    val ratings: List<Double>,
    val seller: Seller? = null
) : Serializable

data class Seller(
    val id: String,
    val name: String,
    val image: String
) : Serializable