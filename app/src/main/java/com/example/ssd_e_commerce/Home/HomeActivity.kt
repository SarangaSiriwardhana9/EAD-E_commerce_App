package com.example.ssd_e_commerce.Home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.CartActivity
import com.example.ssd_e_commerce.ProductAdapter
import com.example.ssd_e_commerce.ProductDetail.MainImageSliderAdapter
import com.example.ssd_e_commerce.NotificationsActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.ActivityHomeBinding
import com.example.ssd_e_commerce.data.CategoryData
import com.example.ssd_e_commerce.data.ItemData

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<CategoryItem>()
    private lateinit var categoryRunnable: Runnable
    private val categoryHandler = android.os.Handler()

    private lateinit var menuIcon: ImageView
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupHomeScreen()
        setupVoucherAndDiscount()
    }
    //bottom navigation bar
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
    //home screen
    private fun setupHomeScreen() {
        setupSearchBar()
        setupImageSlider()
        setupCategorySlider()
        setupFlashDealSlider() // New method
        setupItemCards()
    }
    //voucher and discount
    private fun setupVoucherAndDiscount() {
        binding.tvMoreVouchers.setOnClickListener {
            Toast.makeText(this, "More vouchers clicked", Toast.LENGTH_SHORT).show()
            // Implement navigation to vouchers list screen
        }

        binding.btnCollectAll.setOnClickListener {
            Toast.makeText(this, "Vouchers collected", Toast.LENGTH_SHORT).show()
            // Implement voucher collection logic
        }
    }
    private fun setupFlashDealSlider() {
        val flashDealProducts = ItemData.products.shuffled().take(10) // Randomly select 10 products
        val adapter = FlashDealAdapter(flashDealProducts)
        binding.flashDealRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.flashDealRecyclerView.adapter = adapter
    }

    //category slider
    private fun setupCategorySlider() {
        categoryAdapter = CategoryAdapter(CategoryData.categories)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Auto slide for categories
        categoryRunnable = object : Runnable {
            override fun run() {
                binding.categoryRecyclerView.smoothScrollToPosition((binding.categoryRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1)
                categoryHandler.postDelayed(this, 3000) // Slide every 3 seconds
            }
        }
        categoryHandler.postDelayed(categoryRunnable, 3000)
    }
    //search bar
    private fun setupSearchBar() {
        menuIcon = findViewById(R.id.menuIcon)
        searchButton = findViewById(R.id.searchButton)

        menuIcon.setOnClickListener {
            // Handle menu icon click
        }

        searchButton.setOnClickListener {
            val query = binding.searchBar.query.toString()
            // Perform search with the query
        }

        binding.searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text changes
                return true
            }
        })
    }
    //image slider
    private fun setupImageSlider() {
        val imageList = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )

        val adapter = MainImageSliderAdapter(imageList)
        binding.viewPager.adapter = adapter

        // Auto slide
        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.viewPager.currentItem
                binding.viewPager.currentItem = if (currentItem == imageList.size - 1) 0 else currentItem + 1
                handler.postDelayed(this, 3000) // Change image every 3 seconds
            }
        }
        handler.postDelayed(runnable, 3000)
    }


    //item cards
    private fun setupItemCards() {
        val items = ItemData.products
        val adapter = ProductAdapter(items)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        categoryHandler.removeCallbacks(categoryRunnable)
    }
}