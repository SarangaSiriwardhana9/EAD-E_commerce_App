package com.example.ssd_e_commerce.Auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.R
import com.google.android.material.button.MaterialButton

class AccountPendingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_pending)

        val backToLoginButton: MaterialButton = findViewById(R.id.backToLoginButton)

        backToLoginButton.setOnClickListener {
            val intent = Intent(this@AccountPendingActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}