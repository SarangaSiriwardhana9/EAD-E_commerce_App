package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.ProductDetail.Product
import com.example.ssd_e_commerce.R

object ItemData {
    val products = listOf(
        Product("1", "Item 1", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 1", 19.99, listOf(R.drawable.flash_item1, R.drawable.flash_item2), SellerData.sellers[0]),
        Product("2", "Item 2", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 2", 29.99, listOf(R.drawable.flash_item3, R.drawable.flash_item4), SellerData.sellers[1]),
        Product("3", "Item 3", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 1", 39.99, listOf(R.drawable.flash_item5, R.drawable.flash_item6), SellerData.sellers[0]),
        Product("4", "Item 4", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 3", 49.99, listOf(R.drawable.flash_item1, R.drawable.flash_item1), SellerData.sellers[1])
    )
}