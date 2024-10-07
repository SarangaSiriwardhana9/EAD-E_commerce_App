package com.example.ssd_e_commerce.Home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssd_e_commerce.ProductAdapter
import com.example.ssd_e_commerce.data.ItemData
import com.example.ssd_e_commerce.databinding.ActivityCategoryProductsBinding

class CategoryProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("CATEGORY") ?: return

        setupCustomToolbar(category)
        displayCategoryProducts(category)
    }

    private fun setupCustomToolbar(category: String) {
        // Set category name in the custom toolbar
        binding.categoryTitle.text = category

        // Handle back button click
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun displayCategoryProducts(category: String) {
        val categoryProducts = ItemData.products.filter { it.category == category }
        val adapter = ProductAdapter(categoryProducts)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }
}
