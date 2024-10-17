package com.example.ssd_e_commerce

import android.util.Log
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.models.AddToCartRequest
import com.example.ssd_e_commerce.models.UpdateCartRequest
import com.example.ssd_e_commerce.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartManager(private val userRepository: UserRepository, private val customerId: String) {
    private var cartItems = mutableListOf<CartItem>()
    private var cartId: String? = null

    suspend fun loadCart() {
        withContext(Dispatchers.IO) {
            try {
                Log.d("CartManager", "Attempting to load cart for customerId: $customerId")
                val cartResponse = userRepository.getCustomerCart(customerId)
                Log.d("CartManager", "Cart response received: $cartResponse")
                cartId = cartResponse.data.id
                cartItems = cartResponse.data.items.map { item ->
                    val product = userRepository.getProductDetails(item.productId)
                    CartItem(product, item.quantity)
                }.toMutableList()
                Log.d("CartManager", "Cart loaded successfully with ${cartItems.size} items")
            } catch (e: Exception) {
                Log.e("CartManager", "Error loading cart for userId: $customerId", e)
                when (e) {
                    is retrofit2.HttpException -> {
                        Log.e("CartManager", "HTTP Error: ${e.code()}")
                        Log.e("CartManager", "Error Body: ${e.response()?.errorBody()?.string()}")
                    }
                    else -> Log.e("CartManager", "Unexpected error: ${e.message}")
                }
                throw e
            }
        }
    }

    suspend fun addToCart(product: Product, quantity: Int = 1) {
        withContext(Dispatchers.IO) {
            val existingItem = cartItems.find { it.product.id == product.id }
            if (existingItem != null) {
                val newQuantity = existingItem.quantity + quantity
                val updateRequest = UpdateCartRequest(customerId, product.id, newQuantity)
                userRepository.updateCart(cartId!!, updateRequest)
                existingItem.quantity = newQuantity
            } else {
                val addRequest = AddToCartRequest(customerId, product.id, quantity)
                userRepository.createCart(addRequest)
                cartItems.add(CartItem(product, quantity))
            }
        }
    }

    suspend fun removeFromCart(productId: String) {
        withContext(Dispatchers.IO) {
            userRepository.deleteCartItem(cartId!!)
            cartItems.removeAll { it.product.id == productId }
        }
    }

    suspend fun updateQuantity(productId: String, quantity: Int) {
        withContext(Dispatchers.IO) {
            val updateRequest = UpdateCartRequest(customerId, productId, quantity)
            userRepository.updateCart(cartId!!, updateRequest)
            val item = cartItems.find { it.product.id == productId }
            item?.quantity = quantity
        }
    }

    fun getCartItems(): List<CartItem> = cartItems.toList()

    fun getTotal(): Double = cartItems.sumOf { it.product.price * it.quantity }

    fun clearCart() {
        cartItems.clear()
        cartId = null
    }
}

data class CartItem(val product: Product, var quantity: Int)