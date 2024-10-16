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

        val category = intent.getStringExtra("CATEGORY") ?: return

        setupCustomToolbar(category)
        fetchCategoryProducts(category)
    }

    private fun setupCustomToolbar(category: String) {
        binding.categoryTitle.text = category
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchCategoryProducts(category: String) {
        lifecycleScope.launch {
            try {
                val products = userRepository.getProductsByCategory(category)
                displayCategoryProducts(products)
            } catch (e: Exception) {
                Toast.makeText(this@CategoryProductsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayCategoryProducts(products: List<Product>) {
        val adapter = ProductAdapter(products)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }
}