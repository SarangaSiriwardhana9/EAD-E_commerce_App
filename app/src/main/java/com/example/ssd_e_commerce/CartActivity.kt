package com.example.ssd_e_commerce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.Home.HomeActivity
import com.example.ssd_e_commerce.Profile.ProfileActivity
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.databinding.CartItemBinding
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    lateinit var cartManager: CartManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val sessionManager = SessionManager(this)
            val userRepository = UserRepository(sessionManager)
            val userId = sessionManager.fetchUserId()

            // Log the userId for debugging
            Log.d("CartActivity", "User ID: $userId")

            if (userId == null) {
                throw Exception("User ID is null")
            }
            cartManager = CartManager(userRepository, userId)

            setupRecyclerView()
            setupCheckoutButton()
            setupBottomNavigation()
            loadCartItems()
        } catch (e: Exception) {
            Log.e("CartActivity", "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Error initializing cart: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(emptyList(), { updateTotalPrice() }, { updateCartItems() })
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener {
            val intent = Intent(this, PlaceOrderActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateTotalPrice() {
        val total = cartManager.getTotal()
        binding.subtotalTextView.text = String.format("Subtotal: Rs. %.2f", total)
        binding.checkoutButton.text = String.format("Check Out (%.2f)", total)
    }

    private fun updateCartItems() {
        lifecycleScope.launch {
            try {
                cartManager.loadCart()
                cartAdapter.updateItems(cartManager.getCartItems())
                updateTotalPrice()
                updateCartVisibility()
            } catch (e: Exception) {
                Log.e("CartActivity", "Error updating cart items: ${e.message}", e)
                Toast.makeText(this@CartActivity, "Error updating cart: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCartItems() {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                cartManager.loadCart()
                cartAdapter.updateItems(cartManager.getCartItems())
                updateTotalPrice()
                updateCartVisibility()
            } catch (e: Exception) {
                // Log detailed error messages
                Log.e("CartActivity", "Error loading cart items: ${e.message}", e)
                Toast.makeText(this@CartActivity, "Error loading cart: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun updateCartVisibility() {
        if (cartManager.getCartItems().isEmpty()) {
            binding.emptyCartMessage.visibility = View.VISIBLE
            binding.cartRecyclerView.visibility = View.GONE
            binding.subtotalTextView.visibility = View.GONE
            binding.checkoutButton.visibility = View.GONE
        } else {
            binding.emptyCartMessage.visibility = View.GONE
            binding.cartRecyclerView.visibility = View.VISIBLE
            binding.subtotalTextView.visibility = View.VISIBLE
            binding.checkoutButton.visibility = View.VISIBLE
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_cart
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
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
    private var cartItems: List<CartItem>,
    private val onItemChanged: () -> Unit,
    private val onItemDeleted: () -> Unit
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
                .load(item.product.images.firstOrNull())
                .into(productImageView)

            increaseButton.setOnClickListener {
                (holder.itemView.context as? CartActivity)?.lifecycleScope?.launch {
                    (holder.itemView.context as? CartActivity)?.cartManager?.updateQuantity(item.product.id, item.quantity + 1)
                    notifyItemChanged(position)
                    onItemChanged()
                }
            }

            decreaseButton.setOnClickListener {
                if (item.quantity > 1) {
                    (holder.itemView.context as? CartActivity)?.lifecycleScope?.launch {
                        (holder.itemView.context as? CartActivity)?.cartManager?.updateQuantity(item.product.id, item.quantity - 1)
                        notifyItemChanged(position)
                        onItemChanged()
                    }
                }
            }

            deleteButton.setOnClickListener {
                (holder.itemView.context as? CartActivity)?.lifecycleScope?.launch {
                    (holder.itemView.context as? CartActivity)?.cartManager?.removeFromCart(item.product.id)
                    onItemDeleted()
                }
            }
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateItems(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}
