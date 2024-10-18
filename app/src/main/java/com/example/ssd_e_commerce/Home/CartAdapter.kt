package com.example.ssd_e_commerce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.databinding.ItemCartProductBinding
import com.example.ssd_e_commerce.models.Cart
import com.example.ssd_e_commerce.models.Product

class CartAdapter(
    private val onQuantityChanged: (String, String, Int) -> Unit,
    private val onItemDeleted: (String, String) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartId: String = ""
    private var products = listOf<Pair<Product, Int>>()
    private lateinit var currentCart: Cart

    fun submitList(cartId: String, productList: List<Pair<Product, Int>>) {
        this.cartId = cartId
        products = productList
        notifyDataSetChanged()
    }

    fun getCurrentCart(): Cart = currentCart

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (product, quantity) = products[position]
        holder.bind(product, quantity)
    }

    override fun getItemCount(): Int = products.size

    inner class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, quantity: Int) {
            binding.productName.text = product.name
            binding.productPrice.text = "₹${product.price}"
            binding.quantityTextView.text = quantity.toString()

            Glide.with(binding.productImage.context)
                .load(product.images.firstOrNull())
                .into(binding.productImage)

            binding.increaseButton.setOnClickListener {
                onQuantityChanged(cartId, product.id, quantity + 1)
            }

            binding.decreaseButton.setOnClickListener {
                if (quantity > 1) {
                    onQuantityChanged(cartId, product.id, quantity - 1)
                }
            }

            binding.deleteButton.setOnClickListener {
                onItemDeleted(cartId, product.id)
            }
        }
    }
}