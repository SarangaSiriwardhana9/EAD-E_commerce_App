package com.example.ssd_e_commerce.api


import com.example.ssd_e_commerce.models.CartListResponse
import com.example.ssd_e_commerce.models.CartRequest
import com.example.ssd_e_commerce.models.CartResponse
import com.example.ssd_e_commerce.models.CategoryResponse
import com.example.ssd_e_commerce.models.DeleteOrderResponse
import com.example.ssd_e_commerce.models.LoginResponse
import com.example.ssd_e_commerce.models.NotificationResponse
import com.example.ssd_e_commerce.models.OrderListResponse
import com.example.ssd_e_commerce.models.OrderRequest
import com.example.ssd_e_commerce.models.OrderResponse
import com.example.ssd_e_commerce.models.ProductResponse
import com.example.ssd_e_commerce.models.ReviewListResponse
import com.example.ssd_e_commerce.models.ReviewRequest
import com.example.ssd_e_commerce.models.ReviewResponse
import com.example.ssd_e_commerce.models.SingleProductResponse
import com.example.ssd_e_commerce.models.UpdateCartRequest
import com.example.ssd_e_commerce.models.UserResponse
import com.example.ssd_e_commerce.models.VendorResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/User/login")
    suspend fun login(@Body loginRequest: Map<String, String>): LoginResponse

    @POST("api/User/self-register")
    suspend fun register(@Body registerRequest: Map<String, String>): LoginResponse

    @GET("api/User/check-approval")
    suspend fun checkApproval(): LoginResponse

    @GET("api/Product")
    suspend fun getProducts(@Header("Authorization") token: String): ProductResponse

    @GET("api/Product")
    suspend fun getProductsByCategory(
        @Header("Authorization") token: String,
        @Query("category") category: String
    ): ProductResponse

    @GET("api/User/vendor/{id}")
    suspend fun getVendorDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): VendorResponse

    @GET("api/Review/vendor/{id}")
    suspend fun getVendorReviews(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ReviewListResponse

    @POST("api/Review/create")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Body reviewRequest: ReviewRequest
    ): ReviewResponse

    @GET("api/User/{id}")
    suspend fun getUserDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): UserResponse

    @GET("api/Category")
    suspend fun getCategories(@Header("Authorization") token: String): CategoryResponse




    @GET("api/Product/{id}")
    suspend fun getProductById(@Header("Authorization") token: String, @Path("id") id: String): SingleProductResponse

    @POST("api/Order/create")
    suspend fun createOrder(@Header("Authorization") token: String, @Body orderRequest: OrderRequest): OrderResponse

    @GET("api/Notification/user/{userId}")
    suspend fun getUserNotifications(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): NotificationResponse

    @GET("api/Order/customer/{customerId}")
    suspend fun getCustomerOrders(
        @Header("Authorization") token: String,
        @Path("customerId") customerId: String
    ): OrderListResponse

    @DELETE("api/Order/delete/{orderId}")
    suspend fun deleteOrder(
        @Header("Authorization") token: String,
        @Path("orderId") orderId: String
    ): DeleteOrderResponse




    @GET("api/ShoppingCart/customer/{customerId}")
    suspend fun getCart(
        @Header("Authorization") token: String,
        @Path("customerId") customerId: String
    ): CartResponse

    @GET("api/ShoppingCart")
    suspend fun getAllCarts(@Header("Authorization") token: String): CartListResponse

    @POST("api/ShoppingCart/create")
    suspend fun createCart(@Header("Authorization") token: String, @Body cartRequest: CartRequest): CartResponse

    @PUT("api/ShoppingCart/update/{id}")
    suspend fun updateCart(@Header("Authorization") token: String, @Path("id") id: String, @Body updateCartRequest: UpdateCartRequest): CartResponse

    @DELETE("api/ShoppingCart/delete/{id}")
    suspend fun deleteCart(@Header("Authorization") token: String, @Path("id") id: String): CartResponse


}
