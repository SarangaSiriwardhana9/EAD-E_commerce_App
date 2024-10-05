package com.example.ssd_e_commerce.ProductDetail
import java.io.Serializable

data class Product(
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val images: List<Any>,
    val sellerName: String
) : Serializable