package com.example.ssd_e_commerce.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.CartActivity
import com.example.ssd_e_commerce.ProductAdapter
import com.example.ssd_e_commerce.ProductDetail.MainImageSliderAdapter
import com.example.ssd_e_commerce.ProductDetail.Product
import com.example.ssd_e_commerce.ProductDetail.ProductDetailActivity
import com.example.ssd_e_commerce.NotificationsActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.SearchActivity
import com.example.ssd_e_commerce.databinding.ActivityHomeBinding
import com.example.ssd_e_commerce.data.CategoryData
import com.example.ssd_e_commerce.data.ItemData

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<CategoryItem>()
    private lateinit var categoryRunnable: Runnable
    private val categoryHandler = android.os.Handler()

    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
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
        setupFlashDealSlider()
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

    private fun setupFlashDealSlider() {
        val flashDealProducts = ItemData.products.shuffled().take(10) // Randomly select 10 products
        val adapter = FlashDealAdapter(flashDealProducts)
        binding.flashDealRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.flashDealRecyclerView.adapter = adapter
    }

    private fun setupCategorySlider() {
        categoryAdapter = CategoryAdapter(CategoryData.categories)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Auto slide for categories
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

                categoryHandler.postDelayed(this, 3000) // Slide every 3 seconds
            }
        }
        categoryHandler.postDelayed(categoryRunnable, 3000)
    }

    private fun setupSearchBar() {
        searchButton = findViewById(R.id.searchButton)

        // Create a SimpleCursorAdapter
        val from = arrayOf("productName", "productImage")
        val to = intArrayOf(R.id.suggestionText, R.id.suggestionImage)
        val cursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.search_suggestion_item,
            null,
            from,
            to,
            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        cursorAdapter.setViewBinder { view, cursor, columnIndex ->
            if (columnIndex == 2) { // productImage column
                val imageView = view as ImageView
                val imageResource = cursor.getInt(columnIndex)
                Glide.with(this)
                    .load(imageResource)
                    .into(imageView)
                true
            } else {
                false
            }
        }

        // Set the CursorAdapter
        binding.searchBar.suggestionsAdapter = cursorAdapter

        searchButton.setOnClickListener {
            performSearch(binding.searchBar.query.toString())
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, "productName", "productImage", "productId"))
                newText?.let {
                    ItemData.products.forEachIndexed { index, product ->
                        if (product.name.contains(newText, true)) {
                            cursor.addRow(arrayOf(index, product.name, product.images.first(), product.id))
                            if (cursor.count >= 3) {
                                return@forEachIndexed
                            }
                        }
                    }
                }
                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        binding.searchBar.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = binding.searchBar.suggestionsAdapter.getItem(position) as android.database.Cursor
                val productId = cursor.getString(cursor.getColumnIndex("productId"))
                val product = ItemData.products.find { it.id == productId }
                if (product != null) {
                    navigateToProductDetail(product)
                }
                return true
            }
        })
    }

    private fun performSearch(query: String?) {
        if (!query.isNullOrBlank()) {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("SEARCH_QUERY", query)
            startActivity(intent)
        }
    }

    private fun navigateToProductDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("ITEM", product)
        startActivity(intent)
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