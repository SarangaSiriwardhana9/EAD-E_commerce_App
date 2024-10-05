package com.example.ssd_e_commerce.data

import java.io.Serializable

data class Seller(
    val id: String,
    val name: String,
    val email: String,
    val mobileNo: String,
    val address: String,
    val shopAddress: String,
    val shopEmail: String,
    var rating: Float,
    val comments: List<Comment>,
    val image: Int
) : Serializable

data class Comment(
    val userId: String,
    val userName: String,
    val content: String,
    val rating: Float,
    val timestamp: Long
) : Serializable