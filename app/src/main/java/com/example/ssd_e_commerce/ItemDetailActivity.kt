// ItemDetailActivity.kt
package com.example.ssd_e_commerce

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ssd_e_commerce.databinding.ActivityItemDetailBinding

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra("ITEM") as? Item
        item?.let {
            binding.itemDetailImage.setImageResource(it.imageResId)
            binding.itemDetailName.text = it.name
            binding.itemDetailDescription.text = it.description
            binding.itemDetailPrice.text = "Price: $${it.price}"
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}