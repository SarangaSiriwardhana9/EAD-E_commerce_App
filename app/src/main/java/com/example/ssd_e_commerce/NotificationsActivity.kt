package com.example.ssd_e_commerce

import android.os.Bundle
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
        notificationAdapter = NotificationAdapter()
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

                // Sort notifications by creation date (latest first)
                val sortedNotifications = notifications.sortedByDescending { it.createdAt }

                notificationAdapter.submitList(sortedNotifications)
            } catch (e: Exception) {
                // Handle error (e.g., show error message)
            }
        }
    }

}