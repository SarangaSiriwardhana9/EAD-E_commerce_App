package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.ProductDetail.Product
import com.example.ssd_e_commerce.R

object ItemData {
    val products = listOf(
        Product("Item 1", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera &amp; Video, Brightest Display Screen, Long Battery Life", "Category 1", 19.99, listOf(R.drawable.flash_item1, R.drawable.flash_item2), "Seller 1"),
        Product("Item 2", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera &amp; Video, Brightest Display Screen, Long Battery Life", "Category 2", 29.99, listOf(R.drawable.flash_item3, R.drawable.flash_item4), "Seller 2"),
        Product("Item 3", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera &amp; Video, Brightest Display Screen, Long Battery Life", "Category 1", 39.99, listOf(R.drawable.flash_item5, R.drawable.flash_item6), "Seller 3"),
        Product("Item 4", "I13 Pro Max Cell Phones, 6.3 Inch HD Cellphones, Mobile Phones Face ID, Dual Standby Card Slot, HD Camera &amp; Video, Brightest Display Screen, Long Battery Life", "Category 3", 49.99, listOf(R.drawable.flash_item1, R.drawable.flash_item1), "Seller 4")
    )
}