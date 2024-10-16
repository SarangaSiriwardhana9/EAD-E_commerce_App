package com.example.ssd_e_commerce.ProductDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ImageSliderAdapter
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.ActivityProductDetailBinding
import com.example.ssd_e_commerce.models.Product

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra("ITEM") as? Product
        product?.let {
            setupImageSlider(it.images)
            binding.itemDetailName.text = it.name
            binding.itemDetailPrice.text = "LKR ${it.price}"
            binding.discountInfo.text = "ChoiceDay -61% | Save LKR${it.price * 0.61}"
            binding.itemDetailSeller.text = it.vendorId // Assuming vendorId is the seller name for now
            binding.itemDetailDescription.text = it.description

            // Placeholder for seller image
            Glide.with(this)
                .load(R.drawable.seller)
                .circleCrop()
                .into(binding.sellerAvatar)

            binding.addToCartButton.setOnClickListener {
                // Placeholder for add to cart functionality
                Toast.makeText(this, "Added to cart (not implemented)", Toast.LENGTH_SHORT).show()
            }

            binding.buyNowButton.setOnClickListener {
                // Placeholder for buy now functionality
                Toast.makeText(this, "Buy now (not implemented)", Toast.LENGTH_SHORT).show()
            }

            binding.itemDetailSeller.setOnClickListener {
                // Placeholder for seller detail functionality
                Toast.makeText(this, "Seller details (not implemented)", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            // Placeholder for share functionality
            Toast.makeText(this, "Share functionality (not implemented)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupImageSlider(images: List<String>) {
        val adapter = ImageSliderAdapter(this, images)
        binding.imageSlider.adapter = adapter
    }
}