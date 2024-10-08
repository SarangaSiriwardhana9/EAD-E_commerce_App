package com.example.ssd_e_commerce

import NotificationAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.Home.HomeActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.data.ItemData
import com.example.ssd_e_commerce.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupNotifications()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.titleTextView.text = "Orders"
    }

    private fun setupNotifications() {
        val notifications = generateSampleNotifications()
        val adapter = NotificationAdapter(notifications)
        binding.notificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notificationsRecyclerView.adapter = adapter
    }

    private fun generateSampleNotifications(): List<Notification> {
        return listOf(
            Notification(
                orderId = "216443417900104",
                product = ItemData.products[0],  // Assuming the first product for this example
                quantity = 1,  // Set quantity as needed
                status = "Packed",  // Example status
                timestamp = System.currentTimeMillis()
            )
            // Add more sample notifications here if needed
        )
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_notifications
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_notifications -> true
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
