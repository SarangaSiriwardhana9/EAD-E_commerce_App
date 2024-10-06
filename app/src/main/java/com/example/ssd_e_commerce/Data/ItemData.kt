package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.ProductDetail.Product
import com.example.ssd_e_commerce.R

object ItemData {
    private fun generateRandomDiscount(): Int = (0..70).random()
    private fun generateRandomSoldCount(): Int = (100..10000).random()

    val products = listOf(
        Product("1", "Blancpain Watch", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Electronics", 19.00, listOf(R.drawable.flash_item1, R.drawable.flash_item2), SellerData.sellers[0], generateRandomDiscount(), generateRandomSoldCount()),
        Product("2", "iphone 16", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Electronics", 29.00, listOf(R.drawable.flash_item3, R.drawable.flash_item4), SellerData.sellers[1], generateRandomDiscount(), generateRandomSoldCount()),
        Product("3", "OG CCTV", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Electronics", 39.00, listOf(R.drawable.flash_item5, R.drawable.flash_item6), SellerData.sellers[0], generateRandomDiscount(), generateRandomSoldCount()),
        Product("4", "Blancpain Watch", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera & Video, Brightest Display Screen, Long Battery Life", "Jewelry", 49.00, listOf(R.drawable.flash_item1, R.drawable.flash_item1), SellerData.sellers[1], generateRandomDiscount(), generateRandomSoldCount()),
        Product("5", "T-Shirt", "Comfortable cotton T-shirt with modern design", "Clothing", 25.00, listOf(R.drawable.flash_item2, R.drawable.flash_item3), SellerData.sellers[0], generateRandomDiscount(), generateRandomSoldCount()),
        Product("6", "Lipstick", "Long-lasting matte lipstick", "Makeup", 15.00, listOf(R.drawable.flash_item4, R.drawable.flash_item5), SellerData.sellers[1], generateRandomDiscount(), generateRandomSoldCount()),
        Product("7", "Teddy Bear", "Soft and cuddly teddy bear", "Toys", 20.00, listOf(R.drawable.flash_item6, R.drawable.flash_item1), SellerData.sellers[0], generateRandomDiscount(), generateRandomSoldCount()),
        Product("8", "Running Shoes", "Comfortable and durable running shoes", "Shoes", 80.00, listOf(R.drawable.flash_item2, R.drawable.flash_item3), SellerData.sellers[1], generateRandomDiscount(), generateRandomSoldCount())
    )
}