package com.example.ssd_e_commerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.utils.SessionManager

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupUserInfo()
    }

    private fun setupUserInfo() {
        val userId = sessionManager.fetchUserId()
        binding.textUserId.text = "User ID: $userId"
    }
}