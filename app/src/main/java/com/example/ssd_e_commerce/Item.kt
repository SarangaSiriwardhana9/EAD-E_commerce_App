// Item.kt
package com.example.ssd_e_commerce

import java.io.Serializable

data class Item(
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
) : Serializable