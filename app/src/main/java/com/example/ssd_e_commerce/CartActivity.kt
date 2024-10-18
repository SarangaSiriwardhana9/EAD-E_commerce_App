package com.example.ssd_e_commerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ssd_e_commerce.databinding.ActivityCartBinding
import com.example.ssd_e_commerce.models.Cart
import com.example.ssd_e_commerce.models.CartItemRequest
import com.example.ssd_e_commerce.models.Product
import com.example.ssd_e_commerce.models.UpdateCartRequest
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var userRepository: UserRepository
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        userRepository = UserRepository(sessionManager)

        cartAdapter = CartAdapter(
            onQuantityChanged = { cartId, productId, newQuantity ->
                updateCartItem(cartId, productId, newQuantity)
            },
            onItemDeleted = { cartId, productId ->
                deleteCartItem(cartId, productId)
            }
        )
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        setupToolbar()
        fetchCartDetails()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.titleTextView.text = "Cart"
    }

    private fun fetchCartDetails() {
        val userId = sessionManager.fetchUserId() ?: return

        lifecycleScope.launch {
            try {
                val cartListResponse = userRepository.getAllCarts()
                val userCart = cartListResponse.data.find { it.customerId == userId }
                if (userCart != null) {
                    fetchProductDetails(userCart)
                } else {
                    Toast.makeText(this@CartActivity, "No cart found for the user", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CartActivity, "Failed to load cart: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun fetchProductDetails(cart: Cart) {
        val productList = mutableListOf<Pair<Product, Int>>()
        for (cartItem in cart.items) {
            val product = userRepository.getProductDetails(cartItem.productId)
            productList.add(Pair(product, cartItem.quantity))
        }
        cartAdapter.submitList(cart.id, productList)
        updateTotalPrice(productList)
    }

    private fun updateTotalPrice(productList: List<Pair<Product, Int>>) {
        val total = productList.sumOf { it.first.price * it.second }
        binding.totalPriceTextView.text = String.format("Total: $%.2f", total)
    }

    private fun updateCartItem(cartId: String, productId: String, newQuantity: Int) {
        lifecycleScope.launch {
            try {
                val currentCart = cartAdapter.getCurrentCart()
                val updatedItems = currentCart.items.map {
                    if (it.productId == productId) it.copy(quantity = newQuantity) else it
                }
                val updateRequest = UpdateCartRequest(currentCart.customerId, updatedItems.map { CartItemRequest(it.productId, it.quantity, it.price) })
                userRepository.updateCart(cartId, updateRequest)
                fetchCartDetails() // Refresh the cart after update
            } catch (e: Exception) {
                Toast.makeText(this@CartActivity, "Failed to update item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteCartItem(cartId: String, productId: String) {
        lifecycleScope.launch {
            try {
                val currentCart = cartAdapter.getCurrentCart()
                val updatedItems = currentCart.items.filter { it.productId != productId }
                val updateRequest = UpdateCartRequest(currentCart.customerId, updatedItems.map { CartItemRequest(it.productId, it.quantity, it.price) })
                userRepository.updateCart(cartId, updateRequest)
                fetchCartDetails() // Refresh the cart after deletion
            } catch (e: Exception) {
                Toast.makeText(this@CartActivity, "Failed to delete item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}