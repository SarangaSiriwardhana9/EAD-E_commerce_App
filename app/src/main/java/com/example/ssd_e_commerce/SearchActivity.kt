package com.example.ssd_e_commerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ssd_e_commerce.databinding.ActivitySearchBinding
import com.example.ssd_e_commerce.data.ItemData

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query = intent.getStringExtra("SEARCH_QUERY") ?: ""
        setupToolbar(query)
        performSearch(query)
    }

    private fun setupToolbar(query: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search Results: $query"

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun performSearch(query: String) {
        val searchResults = ItemData.products.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }

        val adapter = ProductAdapter(searchResults)
        binding.searchResultsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.searchResultsRecyclerView.adapter = adapter
    }
}