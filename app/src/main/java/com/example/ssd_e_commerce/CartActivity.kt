package com.example.ssd_e_commerce

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ssd_e_commerce.Home.HomeActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.databinding.CartItemBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCartItems()
        setupRecyclerView()
        setupCheckoutButton()
        setupBottomNavigation()
        updateTotalPrice()
    }

    private fun setupCartItems() {
        // Mock data - replace with actual cart data in a real app
        cartItems.add(CartItem("Product 1", 19.99, 2, R.drawable.item1))
        cartItems.add(CartItem("Product 2", 29.99, 1, R.drawable.item2))
        cartItems.add(CartItem("Product 3", 39.99, 3, R.drawable.item3))
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartItems) { updateTotalPrice() }
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
        val total = cartItems.sumOf { it.price * it.quantity }
        binding.totalPriceTextView.text = String.format("Total: $%.2f", total)
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

data class CartItem(val name: String, val price: Double, var quantity: Int, val imageResId: Int)

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
            productNameTextView.text = item.name
            priceTextView.text = String.format("$%.2f", item.price)
            quantityTextView.text = item.quantity.toString()
            productImageView.setImageResource(item.imageResId)

            increaseButton.setOnClickListener {
                item.quantity++
                quantityTextView.text = item.quantity.toString()
                onItemChanged()
            }

            decreaseButton.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    quantityTextView.text = item.quantity.toString()
                    onItemChanged()
                }
            }
        }
    }

    override fun getItemCount() = cartItems.size
}