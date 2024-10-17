package com.example.ssd_e_commerce
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.databinding.ActivityMyOrdersBinding

class MyOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed() // Go back to the previous activity
        }

        binding.titleTextView.text = "My Orders" // Set the title for the My Orders page
    }
}