package com.example.ssd_e_commerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.databinding.ActivityNotificationsBinding
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding
    private lateinit var userRepository: UserRepository
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        fetchNotifications()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.titleTextView.text = "Notifications"
    }

    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter { notificationId ->
            deleteNotification(notificationId)
        }
        binding.notificationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationsActivity)
            adapter = notificationAdapter
        }
    }

    private fun fetchNotifications() {
        val sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        lifecycleScope.launch {
            try {
                val notifications = userRepository.getUserNotifications()

                // Filter notifications to include only those of type "order"
                val orderNotifications = notifications.filter { it.type == "Order" }

                // Sort the filtered notifications by creation date (latest first)
                val sortedNotifications = orderNotifications.sortedByDescending { it.createdAt }

                // Submit the sorted list to the adapter
                notificationAdapter.submitList(sortedNotifications)
            } catch (e: Exception) {
                Toast.makeText(this@NotificationsActivity, "Error fetching notifications: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNotification(notificationId: String) {
        lifecycleScope.launch {
            try {
                userRepository.deleteNotification(notificationId)
                Toast.makeText(this@NotificationsActivity, "Notification deleted", Toast.LENGTH_SHORT).show()
                fetchNotifications() // Refresh the list after deletion
            } catch (e: Exception) {
                Toast.makeText(this@NotificationsActivity, "Error deleting notification: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}