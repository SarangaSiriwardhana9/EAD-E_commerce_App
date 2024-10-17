package com.example.ssd_e_commerce.Home

import com.example.ssd_e_commerce.R

data class CategoryItem(
    val id: String,
    val name: String,
    val description: String,
    val status: String
) {
    val imageResourceId: Int
        get() = when (name.toLowerCase()) {
            "clothing" -> R.drawable.clothing
            "toys" -> R.drawable.toys
            "eletronic" -> R.drawable.electronic
            "food" -> R.drawable.food
            "gifts" -> R.drawable.gifts
            else -> R.drawable.default_category // You should add a default category image
        }
}