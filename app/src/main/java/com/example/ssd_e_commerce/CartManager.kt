package com.example.ssd_e_commerce

import com.example.ssd_e_commerce.ProductDetail.Product

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product, quantity: Int = 1) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cartItems.add(CartItem(product, quantity))
        }
    }

    fun removeFromCart(productId: String) {
        cartItems.removeAll { it.product.id == productId }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        val item = cartItems.find { it.product.id == productId }
        item?.quantity = quantity
    }

    fun getCartItems(): List<CartItem> = cartItems.toList()

    fun clearCart() {
        cartItems.clear()
    }

    fun getTotal(): Double = cartItems.sumOf { it.product.price * it.quantity }
}

data class CartItem(val product: Product, var quantity: Int)