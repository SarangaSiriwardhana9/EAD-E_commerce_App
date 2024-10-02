package com.example.ssd_e_commerce.Home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.CartActivity
import com.example.ssd_e_commerce.Item
import com.example.ssd_e_commerce.ItemAdapter
import com.example.ssd_e_commerce.NotificationsActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<CategoryItem>()
    private lateinit var categoryRunnable: Runnable
    private val categoryHandler = android.os.Handler()

    private lateinit var menuIcon: ImageView
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupHomeScreen()
        setupVoucherAndDiscount()
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
        setupSearchBar()
        setupImageSlider()
        setupCategorySlider()
        setupFlashSaleSlider()
        setupItemCards()
    }
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

    private fun setupCategorySlider() {
        categories.addAll(listOf(
            CategoryItem("Electronics", R.drawable.electronic),
            CategoryItem("Clothing", R.drawable.clothing),
            CategoryItem("Makeup", R.drawable.makeup),
            CategoryItem("Toys", R.drawable.toys),
            CategoryItem("Jewelry", R.drawable.jewelry),
            CategoryItem("Shoes", R.drawable.shoes)
        ))

        categoryAdapter = CategoryAdapter(categories)
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

    private fun setupImageSlider() {
        val imageList = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )

        val adapter = ImageSliderAdapter(imageList)
        binding.viewPager.adapter = adapter

        // Auto slide
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = binding.viewPager.currentItem
                binding.viewPager.currentItem = if (currentItem == imageList.size - 1) 0 else currentItem + 1
                handler.postDelayed(this, 3000) // Change image every 3 seconds
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun setupFlashSaleSlider() {
        val flashSaleItems = listOf(
            FlashSaleItem("Flash Item 1", 29.99, 49.99, R.drawable.flash_item1),
            FlashSaleItem("Flash Item 2", 39.99, 59.99, R.drawable.flash_item2),
            FlashSaleItem("Flash Item 3", 19.99, 39.99, R.drawable.flash_item3),
            FlashSaleItem("Flash Item 4", 49.99, 79.99, R.drawable.flash_item4),
            FlashSaleItem("Flash Item 5", 34.99, 69.99, R.drawable.flash_item5)
        )

        val adapter = FlashSaleAdapter(flashSaleItems)
        binding.flashSaleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.flashSaleRecyclerView.adapter = adapter
    }

    private fun setupItemCards() {
        val items = listOf(
            Item("Item 1", "Description 1", 19.99, R.drawable.item1),
            Item("Item 2", "Description 2", 29.99, R.drawable.item2),
            Item("Item 3", "Description 3", 39.99, R.drawable.item3),
            Item("Item 4", "Description 4", 49.99, R.drawable.item4)
        )

        val adapter = ItemAdapter(items)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        categoryHandler.removeCallbacks(categoryRunnable)
    }
}