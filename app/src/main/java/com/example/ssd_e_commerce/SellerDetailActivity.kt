package com.example.ssd_e_commerce.ProductDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.data.Seller
import com.example.ssd_e_commerce.databinding.ActivitySellerDetailBinding

class SellerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seller = intent.getSerializableExtra("SELLER") as? Seller
        seller?.let {
            binding.sellerImage.setImageResource(it.image)
            binding.sellerName.text = it.name
            binding.sellerEmail.text = it.email
            binding.sellerMobile.text = it.mobileNo
            binding.sellerAddress.text = it.address
            binding.sellerShopAddress.text = it.shopAddress
            binding.sellerShopEmail.text = it.shopEmail
            binding.sellerRating.rating = it.rating

            // Set up comments RecyclerView
            val commentsAdapter = CommentsAdapter(it.comments)
            binding.commentsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.commentsRecyclerView.adapter = commentsAdapter
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}