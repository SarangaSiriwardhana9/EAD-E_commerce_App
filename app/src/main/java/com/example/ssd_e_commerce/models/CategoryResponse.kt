package com.example.ssd_e_commerce.models

import com.example.ssd_e_commerce.Home.CategoryItem

data class CategoryResponse(
    val message: String,
    val data: List<CategoryItem>
)