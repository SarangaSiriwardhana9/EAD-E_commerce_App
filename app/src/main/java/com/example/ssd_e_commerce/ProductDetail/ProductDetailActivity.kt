package com.example.ssd_e_commerce.ProductDetail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ImageSliderAdapter
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.Seller.SellerDetailActivity
import com.example.ssd_e_commerce.databinding.ActivityProductDetailBinding
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        val product = intent.getSerializableExtra("ITEM") as? Product
        product?.let {
            setupImageSlider(it.images)
            binding.itemDetailName.text = it.name
            binding.itemDetailPrice.text = "LKR ${it.price}"
            binding.discountInfo.text = "ChoiceDay -61% | Save LKR${it.price * 0.61}"
            binding.itemDetailDescription.text = it.description

            // Fetch and display vendor name
            fetchVendorName(it.vendorId)

            // Placeholder for seller image
            Glide.with(this)
                .load(R.drawable.seller)
                .circleCrop()
                .into(binding.sellerAvatar)

            binding.addToCartButton.setOnClickListener {
                Toast.makeText(this, "Added to cart (not implemented)", Toast.LENGTH_SHORT).show()
            }

            binding.buyNowButton.setOnClickListener {
                Toast.makeText(this, "Buy now (not implemented)", Toast.LENGTH_SHORT).show()
            }

            binding.itemDetailSeller.setOnClickListener {
                val intent = Intent(this, SellerDetailActivity::class.java)
                intent.putExtra("VENDOR_ID", product.vendorId)
                startActivity(intent)
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            Toast.makeText(this, "Share functionality (not implemented)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupImageSlider(images: List<String>) {
        val adapter = ImageSliderAdapter(this, images)
        binding.imageSlider.adapter = adapter
    }

    private fun fetchVendorName(vendorId: String) {
        // Launch a coroutine to fetch vendor details
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val vendorData = userRepository.getVendorDetails(vendorId)
                val vendorName = vendorData.name

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    binding.itemDetailSeller.text = vendorName
                }
            } catch (e: Exception) {
                // Handle any errors (e.g., network error, vendor not found)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProductDetailActivity, "Failed to load vendor details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
