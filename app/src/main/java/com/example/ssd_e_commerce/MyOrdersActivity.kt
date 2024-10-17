package com.example.ssd_e_commerce

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.databinding.ActivityMyOrdersBinding
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrdersBinding
    private lateinit var userRepository: UserRepository
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        fetchOrders()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.titleTextView.text = "My Orders"
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter { orderId ->
            deleteOrder(orderId)
        }
        binding.recyclerViewOrders.apply {
            layoutManager = LinearLayoutManager(this@MyOrdersActivity)
            adapter = orderAdapter
        }
    }

    private fun fetchOrders() {
        val sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val orders = userRepository.getCustomerOrders()
                orderAdapter.submitList(orders)
                binding.progressBar.visibility = View.GONE
                if (orders.isEmpty()) {
                    binding.noOrdersTextView.visibility = View.VISIBLE
                } else {
                    binding.noOrdersTextView.visibility = View.GONE
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                binding.noOrdersTextView.visibility = View.VISIBLE
                binding.noOrdersTextView.text = "Error fetching orders"
                Toast.makeText(this@MyOrdersActivity, "Error fetching orders: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteOrder(orderId: String) {
        lifecycleScope.launch {
            try {
                val response = userRepository.deleteOrder(orderId)
                Toast.makeText(this@MyOrdersActivity, response.message, Toast.LENGTH_SHORT).show()
                fetchOrders() // Refresh the list after deletion
            } catch (e: Exception) {
                Toast.makeText(this@MyOrdersActivity, "Error deleting order: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}