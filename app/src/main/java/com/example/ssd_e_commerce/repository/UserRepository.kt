package com.example.ssd_e_commerce.repository

import com.example.ssd_e_commerce.api.ApiConstants
import com.example.ssd_e_commerce.api.ApiService
import com.example.ssd_e_commerce.models.LoginResponse
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.models.ProductResponse
import com.example.ssd_e_commerce.models.VendorData
import com.example.ssd_e_commerce.utils.SessionManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(private val sessionManager: SessionManager) {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun login(email: String, password: String): LoginResponse =
        apiService.login(mapOf("email" to email, "password" to password))

    suspend fun register(name: String, email: String, password: String): LoginResponse =
        apiService.register(mapOf("name" to name, "email" to email, "password" to password))

    suspend fun checkApproval(): LoginResponse =
        apiService.checkApproval()

    suspend fun getProducts(): List<Product> {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getProducts("Bearer $token").data
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getProductsByCategory("Bearer $token", category).data
    }

    suspend fun getVendorDetails(id: String): VendorData {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getVendorDetails("Bearer $token", id).data
    }
}
