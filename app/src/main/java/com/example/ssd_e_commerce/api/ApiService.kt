package com.example.ssd_e_commerce.api

import com.example.ssd_e_commerce.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/User/login")
    suspend fun login(@Body loginRequest: Map<String, String>): LoginResponse

    @POST("api/User/self-register")
    suspend fun register(@Body registerRequest: Map<String, String>): LoginResponse

    @GET("api/User/check-approval")
    suspend fun checkApproval(): LoginResponse
}