package com.example.ssd_e_commerce.ProductDetail

import com.example.ssd_e_commerce.data.Seller
import java.io.Serializable

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val images: List<Any>,
    val seller: Seller
) : Serializable