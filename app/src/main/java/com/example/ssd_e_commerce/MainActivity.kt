package com.example.ssd_e_commerce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.Auth.LoginActivity
import com.example.ssd_e_commerce.Home.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user is logged in
        if (isLoggedIn()) {
            // If logged in, go to HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            // If not logged in, go to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Close MainActivity
        finish()
    }

    private fun isLoggedIn(): Boolean {
        // Implement your login check logic here
        // For example, check if a user token exists in SharedPreferences
        return false // Change this based on your actual login check
    }
}