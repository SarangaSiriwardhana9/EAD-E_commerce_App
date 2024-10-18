package com.example.ssd_e_commerce.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.databinding.ItemCartProductBinding
import com.example.ssd_e_commerce.models.Product

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val products = mutableListOf<Product>()

    fun submitList(productList: List<Product>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = "₹${product.price}"
            Glide.with(binding.productImage.context)
                .load(product.images.firstOrNull())
                .into(binding.productImage)
        }
    }
}