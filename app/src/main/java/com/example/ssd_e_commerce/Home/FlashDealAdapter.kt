package com.example.ssd_e_commerce.Home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ProductDetail.ProductDetailActivity
import com.example.ssd_e_commerce.databinding.FlashDealItemCardBinding
import com.example.ssd_e_commerce.models.Product

class FlashDealAdapter(private val products: List<Product>) : RecyclerView.Adapter<FlashDealAdapter.FlashDealViewHolder>() {

    class FlashDealViewHolder(private val binding: FlashDealItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.itemName.text = product.name
            binding.itemPrice.text = "Rs. ${product.price}"
            binding.saleTag.text = "SALE"
            binding.soldCount.text = "${product.stockCount} in stock"

            if (product.images.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(product.images.first())
                    .into(binding.itemImage)
            }

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("ITEM", product)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashDealViewHolder {
        val binding = FlashDealItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashDealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlashDealViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}