package com.example.ssd_e_commerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.databinding.ActivityItemDetailBinding

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra("ITEM") as? Item
        item?.let {
            setupImageSlider(it.images)
            binding.itemDetailName.text = it.name
            binding.itemDetailDescription.text = it.description
            binding.itemDetailPrice.text = "Price: $${it.price}"
            binding.itemDetailCategory.text = "Category: ${it.category}"
            binding.itemDetailSeller.text = "Seller: ${it.sellerName}"
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupImageSlider(images: List<Any>) {
        val adapter = ImageSliderAdapter(this, images)
        binding.imageSlider.adapter = adapter
    }
}