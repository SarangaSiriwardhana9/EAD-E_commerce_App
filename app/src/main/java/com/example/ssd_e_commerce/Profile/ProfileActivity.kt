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
import com.example.ssd_e_commerce.utils.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupUserInfo()
        setupOrderStatusBar()
        setupButtons()
        setupBottomNavigation()
    }

    private fun setupUserInfo() {
        binding.textFullName.text = sessionManager.fetchUserName() ?: "N/A"
        binding.textEmail.text = sessionManager.fetchUserEmail() ?: "Email not available"
        binding.textRole.text = sessionManager.fetchUserRole() ?: "Role not available"

    }

    private fun setupOrderStatusBar() {
        // You would typically get this count from your data source
        val toReviewCount = 1
        binding.orderStatusBar.setReviewCount(toReviewCount)
    }

    private fun setupButtons() {
        binding.buttonMyOrders.setOnClickListener {
            Toast.makeText(this, "Navigating to My Orders", Toast.LENGTH_SHORT).show()
        }

        binding.buttonSettings.setOnClickListener {
            Toast.makeText(this, "Navigating to Settings", Toast.LENGTH_SHORT).show()
        }

        binding.buttonHelpCenter.setOnClickListener {
            Toast.makeText(this, "Navigating to Help Center", Toast.LENGTH_SHORT).show()
        }

        binding.buttonLogout.setOnClickListener {
            // Clear the session
            sessionManager.clearSession()

            // Navigate to LoginActivity
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