package com.example.ssd_e_commerce.Home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.*
import com.example.ssd_e_commerce.ProductDetail.MainImageSliderAdapter
import com.example.ssd_e_commerce.ProductDetail.ProductDetailActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.databinding.ActivityHomeBinding
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryRunnable: Runnable
    private val categoryHandler = android.os.Handler()

    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        setupBottomNavigation()
        setupHomeScreen()
        setupVoucherAndDiscount()
        fetchProducts()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_home
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.navigation_notifications -> {
                    startActivity(Intent(this, NotificationsActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupHomeScreen() {
        setupImageSlider()
        setupCategorySlider()
    }

    private fun setupVoucherAndDiscount() {
        binding.tvMoreVouchers.setOnClickListener {
            Toast.makeText(this, "More vouchers clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnCollectAll.setOnClickListener {
            Toast.makeText(this, "Vouchers collected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFlashDealSlider(products: List<Product>) {
        val activeFlashDealProducts = products.filter { it.active }.shuffled().take(10)
        val adapter = FlashDealAdapter(activeFlashDealProducts)
        binding.flashDealRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.flashDealRecyclerView.adapter = adapter
    }

    private fun setupCategorySlider() {
        lifecycleScope.launch {
            try {
                val categories = userRepository.getCategories()
                categoryAdapter = CategoryAdapter(categories) { categoryId, categoryName ->
                    val intent = Intent(this@HomeActivity, CategoryProductsActivity::class.java)
                    intent.putExtra("CATEGORY_ID", categoryId)
                    intent.putExtra("CATEGORY_NAME", categoryName)
                    startActivity(intent)
                }
                binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.categoryRecyclerView.adapter = categoryAdapter

                categoryRunnable = object : Runnable {
                    override fun run() {
                        val layoutManager = binding.categoryRecyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        val totalItemCount = layoutManager.itemCount

                        if (lastVisibleItemPosition < totalItemCount - 1) {
                            binding.categoryRecyclerView.smoothScrollToPosition(lastVisibleItemPosition + 1)
                        } else {
                            binding.categoryRecyclerView.smoothScrollToPosition(0)
                        }

                        categoryHandler.postDelayed(this, 3000)
                    }
                }
                categoryHandler.postDelayed(categoryRunnable, 3000)
            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Error fetching categories: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupImageSlider() {
        val imageList = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )

        val adapter = MainImageSliderAdapter(imageList)
        binding.viewPager.adapter = adapter

        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.viewPager.currentItem
                binding.viewPager.currentItem = if (currentItem == imageList.size - 1) 0 else currentItem + 1
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun setupItemCards(products: List<Product>) {
        val activeProducts = products.filter { it.active }
        val adapter = ProductAdapter(activeProducts)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchProducts() {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val allProducts = userRepository.getProducts()
                val activeProducts = allProducts.filter { it.active }
                if (activeProducts.isNotEmpty()) {
                    setupItemCards(activeProducts)
                    setupFlashDealSlider(activeProducts)
                } else {
                    Toast.makeText(this@HomeActivity, "No active products found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Error fetching products: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.contentLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        categoryHandler.removeCallbacks(categoryRunnable)
    }
}
