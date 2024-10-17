package com.example.ssd_e_commerce.models

data class VendorResponse(
    val message: String,
    val data: VendorData
)

data class VendorData(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val role: String,
    val active: Boolean
)