package com.example.ssd_e_commerce.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val nameEditText: TextInputEditText = findViewById(R.id.nameEditText)
        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val roleSpinner: AutoCompleteTextView = findViewById(R.id.roleSpinner)
        val signupButton: MaterialButton = findViewById(R.id.signupButton)
        val loginTextView: TextView = findViewById(R.id.loginTextView)

        // Set up the spinner
        val roles = arrayOf("Customer", "Seller", "Admin")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roles)
        roleSpinner.setAdapter(adapter)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val role = roleSpinner.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && role.isNotEmpty()) {
                // Handle user creation logic here
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the signup activity
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the login text click listener
        loginTextView.setOnClickListener {
            // Navigate to the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
