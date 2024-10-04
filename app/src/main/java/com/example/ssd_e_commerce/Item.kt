package com.example.ssd_e_commerce
import java.io.Serializable

data class Item(
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val images: List<Any>, // Can be Int (for drawable resource) or String (for URL)
    val sellerName: String
) : Serializable