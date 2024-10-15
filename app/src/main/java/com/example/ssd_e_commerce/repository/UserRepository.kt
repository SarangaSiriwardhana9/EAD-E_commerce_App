package com.example.ssd_e_commerce.repository

import com.example.ssd_e_commerce.api.ApiConstants
import com.example.ssd_e_commerce.api.ApiService
import com.example.ssd_e_commerce.models.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
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
}