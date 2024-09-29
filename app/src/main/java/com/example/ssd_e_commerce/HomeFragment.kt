package com.example.ssd_e_commerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupImageSlider()
        setupFlashSaleSlider()
        setupItemCards()
    }

    private fun setupSearchBar() {
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
        binding.flashSaleRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}