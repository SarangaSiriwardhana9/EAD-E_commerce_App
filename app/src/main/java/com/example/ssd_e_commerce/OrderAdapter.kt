package com.example.ssd_e_commerce

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ssd_e_commerce.databinding.ItemOrderBinding
import com.example.ssd_e_commerce.models.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(private val onDeleteClick: (String) -> Unit) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitSortedList(orders: List<Order>) {
        val sortedOrders = orders.sortedByDescending { it.createdAt }
        submitList(sortedOrders)
    }

    class OrderViewHolder(private val binding: ItemOrderBinding, private val onDeleteClick: (String) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.orderIdTextView.text = "Order ID: ${order.id}"
            binding.totalAmountTextView.text = "Total: LKR ${order.totalAmount}"
            binding.statusTextView.text = "Status: ${order.status}"
            binding.dateTimeTextView.text = "Placed on: ${formatDate(order.createdAt)}"

            // Set the background color based on the order status
            if (order.status.equals("Delivered", ignoreCase = true)) {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.light_green))
            } else {
                // Reset to default background color for other statuses
                binding.root.setCardBackgroundColor(ColorStateList.valueOf(Color.WHITE))
            }

            if (order.status.equals("Pending", ignoreCase = true)) {
                binding.deleteButton.visibility = android.view.View.VISIBLE
                binding.deleteButton.setOnClickListener { onDeleteClick(order.id) }
            } else {
                binding.deleteButton.visibility = android.view.View.GONE
            }
        }

        private fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            return formatter.format(date)
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}