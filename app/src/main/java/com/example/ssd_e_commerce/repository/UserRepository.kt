package com.example.ssd_e_commerce.repository

import com.example.ssd_e_commerce.Home.CategoryItem
import com.example.ssd_e_commerce.api.ApiConstants
import com.example.ssd_e_commerce.api.ApiService
import com.example.ssd_e_commerce.models.AddToCartRequest
import com.example.ssd_e_commerce.models.CartRequest
import com.example.ssd_e_commerce.models.CartResponse
import com.example.ssd_e_commerce.models.LoginResponse
import com.example.ssd_e_commerce.models.Notification
import com.example.ssd_e_commerce.models.OrderRequest
import com.example.ssd_e_commerce.models.OrderResponse
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.models.ProductResponse
import com.example.ssd_e_commerce.models.Review
import com.example.ssd_e_commerce.models.ReviewRequest
import com.example.ssd_e_commerce.models.UpdateCartRequest
import com.example.ssd_e_commerce.models.UserData
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

    suspend fun getVendorReviews(id: String): List<Review> {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getVendorReviews("Bearer $token", id).data
    }

    suspend fun createReview(vendorId: String, rating: Int, comment: String): Review {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        val customerId = sessionManager.fetchUserId() ?: throw Exception("User ID not found")
        val reviewRequest = ReviewRequest(vendorId, customerId, rating, comment)
        return apiService.createReview("Bearer $token", reviewRequest).data
    }

    suspend fun getUserDetails(userId: String): UserData {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getUserDetails("Bearer $token", userId).data
    }

    suspend fun getCategories(): List<CategoryItem> {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getCategories("Bearer $token").data
    }

    suspend fun createCart(cartRequest: CartRequest): CartResponse {
        return apiService.createCart("Bearer ${sessionManager.fetchAuthToken()}", cartRequest)
    }

    suspend fun getCart(customerId: String): CartResponse {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getCart("Bearer $token", customerId)
    }

    suspend fun updateCart(cartId: String, updateCartRequest: UpdateCartRequest): CartResponse {
        return apiService.updateCart("Bearer ${sessionManager.fetchAuthToken()}", cartId, updateCartRequest)
    }

    suspend fun deleteCartItem(cartId: String): CartResponse {
        return apiService.deleteCartItem("Bearer ${sessionManager.fetchAuthToken()}", cartId)
    }

    suspend fun getProductDetails(productId: String): Product {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.getProductById("Bearer $token", productId).data
    }

    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        return apiService.createOrder("Bearer $token", orderRequest)
    }


    suspend fun getUserNotifications(): List<Notification> {
        val token = sessionManager.fetchAuthToken() ?: throw Exception("User not authenticated")
        val userId = sessionManager.fetchUserId() ?: throw Exception("User ID not found")
        return apiService.getUserNotifications("Bearer $token", userId).data
    }
}
