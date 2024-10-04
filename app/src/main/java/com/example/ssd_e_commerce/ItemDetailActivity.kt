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
            binding.itemDetailPrice.text = "LKR ${it.price}"
            binding.discountInfo.text = "ChoiceDay -61% | Save LKR${it.price * 0.61}"
            binding.itemDetailSeller.text = it.sellerName
            binding.itemDetailDescription.text = it.description  // Added this line

            // Set seller avatar
            binding.sellerAvatar.setImageResource(R.drawable.seller)

            // Set up buttons
            binding.addToCartButton.setOnClickListener {
                // Add to cart logic
            }

            binding.buyNowButton.setOnClickListener {
                // Buy now logic
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            // Share logic
        }
    }

    private fun setupImageSlider(images: List<Any>) {
        val adapter = ImageSliderAdapter(this, images)
        binding.imageSlider.adapter = adapter
    }
}

