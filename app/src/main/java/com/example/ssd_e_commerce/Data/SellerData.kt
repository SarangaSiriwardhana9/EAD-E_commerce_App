package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.R

object SellerData {
    val sellers = listOf(
        Seller(
            id = "1",
            name = "Tech Gadgets Store",
            email = "info@techgadgets.com",
            mobileNo = "+1234567890",
            address = "123 Tech Street, Silicon Valley, CA",
            shopAddress = "456 Gadget Avenue, Tech City, CA",
            shopEmail = "shop@techgadgets.com",
            rating = 4.5f,
            comments = listOf(
                Comment("user1", "John Doe", "Great products and fast shipping!", 5f, System.currentTimeMillis()),
                Comment("user2", "Jane Smith", "Good quality, but shipping was a bit slow.", 4f, System.currentTimeMillis() - 86400000)
            ),
            image = R.drawable.seller1
        ),
        Seller(
            id = "2",
            name = "Fashion Trends",
            email = "contact@fashiontrends.com",
            mobileNo = "+9876543210",
            address = "789 Style Road, Fashion District, NY",
            shopAddress = "101 Trendy Lane, Chic City, NY",
            shopEmail = "shop@fashiontrends.com",
            rating = 4.2f,
            comments = listOf(
                Comment("user3", "Alice Johnson", "Love their clothing line!", 5f, System.currentTimeMillis()),
                Comment("user4", "Bob Williams", "Good quality, but sizes run small.", 3f, System.currentTimeMillis() - 172800000)
            ),
            image = R.drawable.seller4
        )
    )
}