package com.example.ssd_e_commerce.Home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssd_e_commerce.ProductAdapter
import com.example.ssd_e_commerce.databinding.ActivityCategoryProductsBinding
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class CategoryProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryProductsBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        val categoryId = intent.getStringExtra("CATEGORY_ID") ?: return
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Category"

        setupCustomToolbar(categoryName)
        fetchCategoryProducts(categoryId)
    }

    private fun setupCustomToolbar(categoryName: String) {
        binding.categoryTitle.text = categoryName
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchCategoryProducts(categoryId: String) {
        lifecycleScope.launch {
            try {
                val allProducts = userRepository.getProducts()
                val categoryProducts = allProducts.filter { it.categoryId == categoryId }
                displayCategoryProducts(categoryProducts)
            } catch (e: Exception) {
                Toast.makeText(this@CategoryProductsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayCategoryProducts(products: List<Product>) {
        if (products.isEmpty()) {
            Toast.makeText(this, "No products found in this category", Toast.LENGTH_SHORT).show()
        }
        val adapter = ProductAdapter(products)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }
}