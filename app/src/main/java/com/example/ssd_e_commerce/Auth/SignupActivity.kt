package com.example.ssd_e_commerce.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.api.ApiConstants
import com.example.ssd_e_commerce.repository.UserRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userRepository = UserRepository()

        val nameEditText: TextInputEditText = findViewById(R.id.nameEditText)
        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val signupButton: MaterialButton = findViewById(R.id.signupButton)
        val loginTextView: TextView = findViewById(R.id.loginTextView)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val response = userRepository.register(name, email, password)
                        Toast.makeText(this@SignupActivity, "Account created successfully", Toast.LENGTH_SHORT).show()
                        // Navigate to LoginActivity
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: HttpException) {
                        // Handle HTTP errors (e.g., 400, 500)
                        Toast.makeText(this@SignupActivity, "Registration failed: ${e.message()}", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Handle other exceptions
                        Toast.makeText(this@SignupActivity, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}