package com.example.ssd_e_commerce.models

data class LoginResponse(
    val message: String,
    val data: LoginData
)

data class LoginData(
    val token: String,
    val role: String,
    val name: String
)