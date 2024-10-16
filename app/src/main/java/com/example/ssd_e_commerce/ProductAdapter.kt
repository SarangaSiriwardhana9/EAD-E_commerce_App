package com.example.ssd_e_commerce

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ProductDetail.ProductDetailActivity
import com.example.ssd_e_commerce.databinding.ProductCardBinding
import com.example.ssd_e_commerce.models.Product

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            if (product.images.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(product.images.first())
                    .into(binding.itemImage)
            }

            binding.itemName.text = product.name
            binding.itemPrice.text = "Rs. ${product.price}"
            binding.itemsSold.text = "${product.stockCount} in stock"

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("ITEM", product)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}