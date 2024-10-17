package com.example.ssd_e_commerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ssd_e_commerce.api.ApiService
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.models.CartItem
import com.example.ssd_e_commerce.models.CartResponse
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Initialize UserRepository for API calls
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.20.10.4")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        userRepository = UserRepository(sessionManager)

        setupUserInfo()
        fetchCartDetails()
    }

    private fun setupUserInfo() {
        val userId = sessionManager.fetchUserId()
        binding.textUserId.text = "User ID: $userId"
    }

    private fun fetchCartDetails() {
        val userId = sessionManager.fetchUserId() ?: return

        lifecycleScope.launch {
            try {
                // Call API to get the cart details
                val cartResponse: CartResponse = userRepository.getCart(userId)

                // Update UI with cart details
                val cartItems = cartResponse.data.items
                val cartDetails = buildCartDetailsString(cartResponse.data.customerId, cartItems)
                binding.textCartScreen.text = cartDetails

            } catch (e: Exception) {
                // Handle errors
                Toast.makeText(this@CartActivity, "Failed to load cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Helper function to build a string from the cart items
    private fun buildCartDetailsString(customerId: String, items: List<CartItem>): String {
        val sb = StringBuilder()
        sb.append("Customer ID: $customerId\n\n")
        sb.append("Items:\n")

        for (item in items) {
            sb.append("Product ID: ${item.productId}\n")
            sb.append("Quantity: ${item.quantity}\n")
            sb.append("Price: ${item.price}\n\n")
        }

        return sb.toString()
    }
}
