package com.example.ssd_e_commerce.models

data class UserResponse(
    val message: String,
    val data: UserData
)

data class UserData(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val role: String,
    val active: Boolean
)