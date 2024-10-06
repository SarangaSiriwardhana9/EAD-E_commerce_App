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

        setupToolbar(category)
        displayCategoryProducts(category)
    }

    private fun setupToolbar(category: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = category

        binding.toolbar.setNavigationOnClickListener {
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