package com.example.ssd_e_commerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.models.CartResponse
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var userRepository: UserRepository
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        // Initialize RecyclerView and its adapter
        cartAdapter = CartAdapter()
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        fetchCartDetails()
    }

    private fun fetchCartDetails() {
        val userId = sessionManager.fetchUserId() ?: return

        lifecycleScope.launch {
            try {
                // Call API to get the cart details
                val cartResponse: CartResponse = userRepository.getCart(userId)

                // Fetch product details for each cart item
                val productList = mutableListOf<Product>()
                for (cartItem in cartResponse.data.items) {
                    val product = userRepository.getProductDetails(cartItem.productId)
                    productList.add(product)
                }

                // Update RecyclerView with product details
                cartAdapter.submitList(productList)

            } catch (e: Exception) {
                // Handle errors
                Toast.makeText(this@CartActivity, "Failed to load cart: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}