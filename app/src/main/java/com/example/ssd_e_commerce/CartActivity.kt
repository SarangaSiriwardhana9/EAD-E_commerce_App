package com.example.ssd_e_commerce

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.Home.HomeActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.databinding.CartItemBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupCheckoutButton()
        setupBottomNavigation()
        updateTotalPrice()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(CartManager.getCartItems()) { updateTotalPrice() }
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener {
            // Implement checkout logic here
        }
    }

    private fun updateTotalPrice() {
        val total = CartManager.getTotal()
        binding.subtotalTextView.text = String.format("Subtotal: Rs. %.2f", total)

        // You can also update the checkout button text here
        binding.checkoutButton.text = String.format("Check Out (%.2f)", total)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_cart
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.navigation_cart -> true
                R.id.navigation_notifications -> {
                    startActivity(Intent(this, NotificationsActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onItemChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.binding.apply {
            productNameTextView.text = item.product.name
            priceTextView.text = String.format("Rs. %.2f", item.product.price)
            quantityTextView.text = item.quantity.toString()

            Glide.with(holder.itemView.context)
                .load(item.product.images.first())
                .into(productImageView)

            increaseButton.setOnClickListener {
                CartManager.updateQuantity(item.product.id, item.quantity + 1)
                notifyItemChanged(position)
                onItemChanged()
            }

            decreaseButton.setOnClickListener {
                if (item.quantity > 1) {
                    CartManager.updateQuantity(item.product.id, item.quantity - 1)
                    notifyItemChanged(position)
                    onItemChanged()
                }
            }
        }
    }

    override fun getItemCount() = cartItems.size
}