package com.example.ssd_e_commerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ssd_e_commerce.databinding.ActivityOrderBinding
import com.example.ssd_e_commerce.models.OrderRequest
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        val product = intent.getSerializableExtra("PRODUCT") as? Product
        if (product == null) {
            Toast.makeText(this, "Error: Product details not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupUserInfo()
        setupProductInfo(product)
        setupConfirmButton(product)
    }

    private fun setupUserInfo() {
        binding.textUserName.text = "Name: ${sessionManager.fetchUserName() ?: "N/A"}"
        binding.textUserId.text = "User ID: ${sessionManager.fetchUserId() ?: "N/A"}"
        binding.textUserEmail.text = "Email: ${sessionManager.fetchUserEmail() ?: "N/A"}"
    }

    private fun setupProductInfo(product: Product) {
        binding.textProductName.text = "Product: ${product.name}"
        binding.textProductPrice.text = "Price: LKR ${product.price}"
    }

    private fun setupConfirmButton(product: Product) {
        binding.buttonConfirmOrder.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val orderRequest = OrderRequest(
                        customerId = sessionManager.fetchUserId() ?: "",
                        totalAmount = product.price,
                        status = "Pending",
                        orderItems = listOf(
                            OrderRequest.OrderItem(
                                productId = product.id,
                                quantity = 1,
                                priceAtPurchase = product.price
                            )
                        ),
                        createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).format(Date()),
                        updatedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).format(Date())
                    )

                    val response = userRepository.createOrder(orderRequest)
                    Toast.makeText(this@OrderActivity, "Order created successfully: ${response.message}", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@OrderActivity, "Failed to create order: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}