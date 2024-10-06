package com.example.ssd_e_commerce.Home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ProductDetail.Product
import com.example.ssd_e_commerce.ProductDetail.ProductDetailActivity
import com.example.ssd_e_commerce.databinding.FlashDealItemCardBinding

class FlashDealAdapter(private val products: List<Product>) : RecyclerView.Adapter<FlashDealAdapter.FlashDealViewHolder>() {

    class FlashDealViewHolder(private val binding: FlashDealItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.itemName.text = product.name
            binding.itemPrice.text = "Rs. ${product.price}"
            binding.saleTag.text = "${product.discountPercentage}%\nOFF"
            binding.soldCount.text = "${product.soldCount} sold"
            binding.sellerName.text = product.seller.name

            val firstImage = product.images.first()
            when (firstImage) {
                is Int -> binding.itemImage.setImageResource(firstImage)
                is String -> Glide.with(binding.root.context)
                    .load(firstImage)
                    .into(binding.itemImage)
            }

            // Load seller image
            Glide.with(binding.root.context)
                .load(product.seller.image)
                .circleCrop()
                .into(binding.sellerImage)

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