package com.example.ssd_e_commerce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ssd_e_commerce.databinding.ItemNotificationBinding
import com.example.ssd_e_commerce.models.Notification
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(private val onDeleteClick: (String) -> Unit) : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        private val onDeleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.notificationTypeTextView.text = notification.type
            binding.notificationMessageTextView.text = notification.message
            binding.notificationTimeTextView.text = formatDate(notification.createdAt)

            // Set card color based on notification type
            val cardColor = when (notification.type) {
                "NewUserRegistration" -> R.color.primary_light
                // Add more types and colors as needed
                else -> R.color.white
            }
            binding.root.setCardBackgroundColor(itemView.context.getColor(cardColor))

            binding.deleteButton.setOnClickListener {
                onDeleteClick(notification.id)
            }
        }

        private fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            return formatter.format(date)
        }
    }

    class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }
}