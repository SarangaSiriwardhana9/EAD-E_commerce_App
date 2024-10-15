package com.example.ssd_e_commerce.repository

import com.example.ssd_e_commerce.api.ApiConstants
import com.example.ssd_e_commerce.api.ApiService
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

    suspend fun login(email: String, password: String) =
        apiService.login(mapOf("email" to email, "password" to password))
}