package com.example.ssd_e_commerce.ProductDetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ImageSliderAdapter
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.Seller.SellerDetailActivity
import com.example.ssd_e_commerce.databinding.ActivityProductDetailBinding

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
            binding.itemDetailSeller.text = it.seller.name
            binding.itemDetailDescription.text = it.description

            // Set seller avatar
            Glide.with(this)
                .load(it.seller.image)
                .placeholder(R.drawable.seller)
                .error(R.drawable.seller)
                .circleCrop()
                .into(binding.sellerAvatar)

            // Set up buttons
            binding.addToCartButton.setOnClickListener {
                // Add to cart logic
            }

            binding.buyNowButton.setOnClickListener {
                // Buy now logic
            }

            // Set up seller click listener
            binding.itemDetailSeller.setOnClickListener {
                val intent = Intent(this, SellerDetailActivity::class.java)
                intent.putExtra("SELLER", product.seller)
                startActivity(intent)
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