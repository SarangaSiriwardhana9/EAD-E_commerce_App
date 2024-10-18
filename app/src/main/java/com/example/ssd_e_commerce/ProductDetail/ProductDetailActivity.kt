package com.example.ssd_e_commerce.ProductDetail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ImageSliderAdapter
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.Seller.SellerDetailActivity
import com.example.ssd_e_commerce.databinding.ActivityProductDetailBinding
import com.example.ssd_e_commerce.models.Product

import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import com.example.ssd_e_commerce.OrderActivity
import com.example.ssd_e_commerce.models.CartItemRequest
import com.example.ssd_e_commerce.models.CartRequest
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        val product = intent.getSerializableExtra("ITEM") as? Product
        product?.let {
            setupImageSlider(it.images)
            binding.itemDetailName.text = it.name // Changed from it.id to it.name
            binding.itemDetailPrice.text = "LKR ${it.price}"
            binding.discountInfo.text = "ChoiceDay -61% | Save LKR${it.price * 0.61}"
            binding.itemDetailDescription.text = it.description

            fetchVendorName(it.vendorId)

            Glide.with(this)
                .load(R.drawable.seller)
                .circleCrop()
                .into(binding.sellerAvatar)

            binding.addToCartButton.setOnClickListener {
                addToCart(product)
            }

            binding.buyNowButton.setOnClickListener {
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("PRODUCT", product)
                startActivity(intent)
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
        lifecycleScope.launch {
            try {
                val vendorData = userRepository.getVendorDetails(vendorId)
                binding.itemDetailSeller.text = vendorData.name
            } catch (e: Exception) {
                Toast.makeText(this@ProductDetailActivity, "Failed to load vendor details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToCart(product: Product) {
        lifecycleScope.launch {
            try {
                val customerId = sessionManager.fetchUserId() ?: throw Exception("User ID not found")
                val cartRequest = CartRequest(
                    customerId = customerId,
                    items = listOf(
                        CartItemRequest(
                            productId = product.id,
                            quantity = 1,
                            price = product.price
                        )
                    )
                )
                val response = userRepository.createCart(cartRequest)
                Toast.makeText(this@ProductDetailActivity, "Added to cart successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ProductDetailActivity, "Failed to add to cart: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}