package com.example.ssd_e_commerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.databinding.ActivityPlaceOrderBinding
import com.example.ssd_e_commerce.databinding.OrderItemBinding
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class PlaceOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceOrderBinding
    private lateinit var orderItemsAdapter: OrderItemsAdapter
    private lateinit var cartManager: CartManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)
        val userRepository = UserRepository(sessionManager)
        cartManager = CartManager(userRepository, sessionManager.fetchUserId()!!)

        setupHeader()
        setupOrderItemsRecyclerView()
        loadOrderItems()
        setupPlaceOrderButton()
    }

    private fun setupHeader() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.categoryTitle.text = "Place Order"
    }

    private fun setupOrderItemsRecyclerView() {
        orderItemsAdapter = OrderItemsAdapter(emptyList())
        binding.orderItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PlaceOrderActivity)
            adapter = orderItemsAdapter
        }
    }

    private fun loadOrderItems() {
        lifecycleScope.launch {
            cartManager.loadCart()
            val cartItems = cartManager.getCartItems()
            orderItemsAdapter.updateItems(cartItems)
            updateOrderSummary()
        }
    }

    private fun updateOrderSummary() {
        val subtotal = cartManager.getTotal()
        val shippingCost = 50.0
        val total = subtotal + shippingCost
        binding.subtotalTextView.text = String.format("Subtotal: Rs. %.2f", subtotal)
        binding.shippingCostTextView.text = String.format("Shipping: Rs. %.2f", shippingCost)
        binding.totalPriceTextView.text = String.format("Total: Rs. %.2f", total)
    }

    private fun setupPlaceOrderButton() {
        binding.placeOrderButton.setOnClickListener {
            lifecycleScope.launch {
                cartManager.clearCart()
                Toast.makeText(this@PlaceOrderActivity, "Order placed successfully!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}

class OrderItemsAdapter(private var orderItems: List<CartItem>) : RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder>() {
    class OrderItemViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val item = orderItems[position]
        holder.binding.apply {
            productNameTextView.text = item.product.name
            quantityTextView.text = "Quantity: ${item.quantity}"
            priceTextView.text = String.format("Rs. %.2f", item.product.price * item.quantity)
            Glide.with(holder.itemView.context)
                .load(item.product.images.firstOrNull())
                .into(productImageView)
        }
    }

    override fun getItemCount() = orderItems.size

    fun updateItems(newItems: List<CartItem>) {
        orderItems = newItems
        notifyDataSetChanged()
    }
}