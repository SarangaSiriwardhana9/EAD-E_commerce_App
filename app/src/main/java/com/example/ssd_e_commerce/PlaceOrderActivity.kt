package com.example.ssd_e_commerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.databinding.ActivityPlaceOrderBinding
import com.example.ssd_e_commerce.databinding.OrderItemBinding

class PlaceOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceOrderBinding
    private lateinit var orderItemsAdapter: OrderItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeader()
        setupOrderItemsRecyclerView()
        updateOrderSummary()
        setupPlaceOrderButton()
    }

    private fun setupHeader() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.categoryTitle.text = "Place Order"
    }

    private fun setupOrderItemsRecyclerView() {
        orderItemsAdapter = OrderItemsAdapter(CartManager.getCartItems())
        binding.orderItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PlaceOrderActivity)
            adapter = orderItemsAdapter
        }
    }

    private fun updateOrderSummary() {
        val subtotal = CartManager.getTotal()
        val shippingCost = 50.0 // Hardcoded shipping cost
        val total = subtotal + shippingCost

        binding.subtotalTextView.text = String.format("Subtotal: Rs. %.2f", subtotal)
        binding.shippingCostTextView.text = String.format("Shipping: Rs. %.2f", shippingCost)
        binding.totalPriceTextView.text = String.format("Total: Rs. %.2f", total)
    }

    private fun setupPlaceOrderButton() {
        binding.placeOrderButton.setOnClickListener {
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()
            CartManager.clearCart()
            finish()
        }
    }
}

class OrderItemsAdapter(private val orderItems: List<CartItem>) : RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder>() {

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
                .load(item.product.images.first())
                .into(productImageView)
        }
    }

    override fun getItemCount() = orderItems.size
}