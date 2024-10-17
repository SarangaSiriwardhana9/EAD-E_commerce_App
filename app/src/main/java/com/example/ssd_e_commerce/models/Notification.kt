package com.example.ssd_e_commerce.models

import java.util.Date

data class Notification(
    val id: String,
    val userId: String,
    val message: String,
    val type: String,
    val readStatus: Boolean,
    val createdAt: Date
)

data class NotificationResponse(
    val message: String,
    val data: List<Notification>
)