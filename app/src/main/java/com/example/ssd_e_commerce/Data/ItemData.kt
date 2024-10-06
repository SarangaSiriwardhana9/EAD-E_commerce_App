package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.ProductDetail.Product
import com.example.ssd_e_commerce.R

object ItemData {
    private fun generateRandomDiscount(): Int = (0..70).random()
    private fun generateRandomSoldCount(): Int = (100..10000).random()

    val products = listOf(
        Product("1", "Item 1", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 1", 19.00, listOf(R.drawable.flash_item1, R.drawable.flash_item2), SellerData.sellers[0], generateRandomDiscount(), generateRandomSoldCount()),
        Product("2", "Item 2", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 2", 29.00, listOf(R.drawable.flash_item3, R.drawable.flash_item4), SellerData.sellers[1], generateRandomDiscount(), generateRandomSoldCount()),
        Product("3", "Item 3", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 1", 39.00, listOf(R.drawable.flash_item5, R.drawable.flash_item6), SellerData.sellers[0], generateRandomDiscount(), generateRandomSoldCount()),
        Product("4", "Item 4", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Category 3", 49.00, listOf(R.drawable.flash_item1, R.drawable.flash_item1), SellerData.sellers[1], generateRandomDiscount(), generateRandomSoldCount())
    )
}