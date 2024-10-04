package com.example.ssd_e_commerce.data

import com.example.ssd_e_commerce.Item
import com.example.ssd_e_commerce.R

object ItemData {
    val items = listOf(
        Item(
            "Item 1",
            "Description 1",
            "Category 1",
            19.99,
            listOf(R.drawable.flash_item2.toString(), "https://www.bing.com/images/search?view=detailV2&ccid=6dTeUzp%2f&id=2BE9459DFB16EA0966B3112DE099609FA24ACFD3&thid=OIP.6dTeUzp_80QlMh2RBusXDwHaHo&mediaurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.e9d4de533a7ff34425321d9106eb170f%3frik%3d089Kop9gmeAtEQ%26riu%3dhttp%253a%252f%252fwww.eaton.com%252fcontent%252fdam%252featon%252fproducts%252ffurniture%252ftechbench-system%252fphoto-gallery%252fTechbench-linear-angle.jpg%26ehk%3dlB5Qh9gu6N%252b2FphfvUd4%252bywRbCo%252b4nIZQDZivz3ru68%253d%26risl%3d1%26pid%3dImgRaw%26r%3d0A1E&itb=0"),
            "Seller 1"
        ),
        Item(
            "Item 2",
            "Description 2",
            "Category 2",
            29.99,
            listOf(R.drawable.flash_item2.toString(),R.drawable.flash_item3.toString(),R.drawable.flash_item2.toString()),
            "Seller 2"
        ),
        Item(
            "Item 3",
            "Description 3",
            "Category 1",
            39.99,
            listOf(R.drawable.flash_item3.toString(), "https://example.com/item3_2.jpg"),
            "Seller 3"
        ),
        Item(
            "Item 4",
            "Description 4",
            "Category 3",
            49.99,
            listOf(R.drawable.flash_item4.toString(), "https://example.com/item4_2.jpg"),
            "Seller 4"
        )
    )
}
