
package com.example.ssd_e_commerce.api

import com.example.ssd_e_commerce.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/User/login")
    suspend fun login(@Body loginRequest: Map<String, String>): LoginResponse
}
