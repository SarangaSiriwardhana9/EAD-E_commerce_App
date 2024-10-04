package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.Item
import com.example.ssd_e_commerce.R

object ItemData {
    val items = listOf(
        Item("Item 1", "Description 1", "Category 1", 19.99, listOf(R.drawable.flash_item1, R.drawable.flash_item2), "Seller 1"),
        Item("Item 2", "Description 2", "Category 2", 29.99, listOf(R.drawable.flash_item3, R.drawable.flash_item4), "Seller 2"),
        Item("Item 3", "Description 3", "Category 1", 39.99, listOf(R.drawable.flash_item5, R.drawable.flash_item6), "Seller 3"),
        Item("Item 4", "Description 4", "Category 3", 49.99, listOf(R.drawable.flash_item1, R.drawable.flash_item1), "Seller 4")
    )
}