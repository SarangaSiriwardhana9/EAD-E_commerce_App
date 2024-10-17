package com.example.ssd_e_commerce.models

import java.util.Date

data class ReviewListResponse(
    val message: String,
    val data: List<Review>
)

data class ReviewResponse(
    val message: String,
    val data: Review
)

data class Review(
    val id: String?,
    val vendorId: String,
    val customerId: String,
    val rating: Int,
    val comment: String,
    val createdAt: Date,
    val updatedAt: Date
)

data class ReviewRequest(
    val vendorId: String,
    val customerId: String,
    val rating: Int,
    val comment: String
)