package com.example.ssd_e_commerce.Seller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ProductDetail.CommentsAdapter
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.data.Comment
import com.example.ssd_e_commerce.databinding.ActivitySellerDetailBinding
import com.example.ssd_e_commerce.models.VendorData
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.launch

class SellerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerDetailBinding
    private lateinit var commentsAdapter: CommentsAdapter
    private var vendor: VendorData? = null
    private var isCommentsExpanded = false
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository(SessionManager(this))

        val vendorId = intent.getStringExtra("VENDOR_ID")
        if (vendorId != null) {
            fetchVendorDetails(vendorId)
        } else {
            Toast.makeText(this, "Vendor ID not provided", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.backButton.setOnClickListener { finish() }
        binding.viewAllComments.setOnClickListener { toggleCommentVisibility() }
    }

    private fun fetchVendorDetails(vendorId: String) {
        lifecycleScope.launch {
            try {
                vendor = userRepository.getVendorDetails(vendorId)
                vendor?.let { setupVendorInfo(it) }
            } catch (e: Exception) {
                Toast.makeText(this@SellerDetailActivity, "Error fetching vendor details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupVendorInfo(vendor: VendorData) {
        Glide.with(this)
            .load(R.drawable.seller_placeholder) // You might want to add an image URL to the VendorData class
            .placeholder(R.drawable.seller_placeholder)
            .error(R.drawable.seller_error)
            .circleCrop()
            .into(binding.sellerImage)

        binding.sellerName.text = vendor.name
        binding.sellerEmail.text = vendor.email
        binding.sellerMobile.text = vendor.phoneNumber
        binding.sellerAddress.text = vendor.address
        binding.sellerShopAddress.text = vendor.address // You might want to add a separate shop address field to VendorData
        binding.sellerShopEmail.text = vendor.email // You might want to add a separate shop email field to VendorData

        // Placeholder data for ratings and comments if not provided
        val placeholderRating = 4.5f
        val placeholderComments = listOf(
            Comment("user1", "John Doe", "Great seller!", 5f, System.currentTimeMillis()),
            Comment("user2", "Jane Smith", "Good products", 4f, System.currentTimeMillis() - 86400000)
        )

        updateRatingAndCommentCount(placeholderRating, placeholderComments)
        setupCommentsRecyclerView(placeholderComments)
        setupCommentSubmission(placeholderRating, placeholderComments)
    }

    private fun setupCommentsRecyclerView(comments: List<Comment>) {
        commentsAdapter = CommentsAdapter(comments.filter { it.content.isNotBlank() }.toMutableList())
        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SellerDetailActivity)
            adapter = commentsAdapter
        }
        updateCommentVisibility()
    }

    private fun setupCommentSubmission(currentRating: Float, currentComments: List<Comment>) {
        binding.submitButton.setOnClickListener {
            val rating = binding.userRating.rating
            val comment = binding.commentEditText.text.toString().trim()

            if (rating == 0f && comment.isBlank()) {
                Toast.makeText(this, "Please provide a rating or comment", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (comment.isNotBlank()) {
                val newComment = Comment(
                    userId = "current_user_id",
                    userName = "Current User",
                    content = comment,
                    rating = rating,
                    timestamp = System.currentTimeMillis()
                )
                commentsAdapter.addComment(newComment)
                updateCommentVisibility()
            }

            // Update rating
            val newRatingSum = currentRating * currentComments.size + rating
            val newRatingCount = currentComments.size + 1
            val newRating = newRatingSum / newRatingCount

            // Update UI
            updateRatingAndCommentCount(newRating, currentComments + listOf(Comment("", "", comment, rating, System.currentTimeMillis())))

            // Clear input fields
            binding.userRating.rating = 0f
            binding.commentEditText.text?.clear()

            Toast.makeText(this, "Submission successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateRatingAndCommentCount(rating: Float, comments: List<Comment>) {
        binding.sellerRating.rating = rating
        binding.ratingCount.text = "${comments.size} ratings"
        val commentCount = comments.count { it.content.isNotBlank() }
        binding.commentCount.text = "$commentCount comments"
    }

    private fun updateCommentVisibility() {
        val commentCount = commentsAdapter.itemCount
        if (commentCount > 2) {
            if (!isCommentsExpanded) {
                (binding.commentsRecyclerView.adapter as CommentsAdapter).limitComments(2)
            }
            binding.viewAllComments.visibility = View.VISIBLE
            binding.viewAllComments.text = if (isCommentsExpanded) "Show less" else "View all $commentCount comments"
        } else {
            binding.viewAllComments.visibility = View.GONE
        }
    }

    private fun toggleCommentVisibility() {
        isCommentsExpanded = !isCommentsExpanded
        if (isCommentsExpanded) {
            (binding.commentsRecyclerView.adapter as CommentsAdapter).showAllComments()
        } else {
            (binding.commentsRecyclerView.adapter as CommentsAdapter).limitComments(2)
        }
        updateCommentVisibility()
    }
}
