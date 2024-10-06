package com.example.ssd_e_commerce.Profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.CartActivity
import com.example.ssd_e_commerce.Home.HomeActivity
import com.example.ssd_e_commerce.Auth.LoginActivity
import com.example.ssd_e_commerce.NotificationsActivity
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUserInfo()
        setupOrderStatusBar()
        setupButtons()
        setupBottomNavigation()
    }

    private fun setupUserInfo() {
        // In a real app, you'd fetch this info from a user session or database
        binding.textFullName.text = "John Doe"
        binding.textEmail.text = "johndoe@example.com"
        binding.textRole.text = "Customer"
    }

    private fun setupOrderStatusBar() {
        // You would typically get this count from your data source
        val toReviewCount = 1
        binding.orderStatusBar.setReviewCount(toReviewCount)
    }

    private fun setupButtons() {
        binding.buttonMyOrders.setOnClickListener {
            // Navigate to My Orders screen (you'll need to create this)
            Toast.makeText(this, "Navigating to My Orders", Toast.LENGTH_SHORT).show()
        }

        binding.buttonSettings.setOnClickListener {
            // Navigate to Settings screen (you'll need to create this)
            Toast.makeText(this, "Navigating to Settings", Toast.LENGTH_SHORT).show()
        }

        binding.buttonHelpCenter.setOnClickListener {
            // Navigate to Help Center screen (you'll need to create this)
            Toast.makeText(this, "Navigating to Help Center", Toast.LENGTH_SHORT).show()
        }

        binding.buttonLogout.setOnClickListener {
            // Perform logout actions (clear session, etc.)
            Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_profile
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.navigation_notifications -> {
                    startActivity(Intent(this, NotificationsActivity::class.java))
                    true
                }
                R.id.navigation_profile -> true
                else -> false
            }
        }
    }
}