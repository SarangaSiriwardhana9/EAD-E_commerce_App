package com.example.ssd_e_commerce

import java.io.Serializable

data class Item(
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val images: List<String>,
    val sellerName: String
) : Serializable