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
import com.example.ssd_e_commerce.models.Review
import com.example.ssd_e_commerce.models.VendorData
import com.example.ssd_e_commerce.repository.UserRepository
import com.example.ssd_e_commerce.utils.SessionManager
import kotlinx.coroutines.async
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
            fetchVendorReviews(vendorId)
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

    private fun fetchVendorReviews(vendorId: String) {
        lifecycleScope.launch {
            try {
                val reviews = userRepository.getVendorReviews(vendorId)

                // Fetch user details for each review asynchronously
                val commentsWithUserNames = reviews.map { review ->
                    async {
                        val user = userRepository.getUserDetails(review.customerId)
                        Comment(review.customerId, user.name, review.comment, review.rating.toFloat(), review.createdAt.time)
                    }
                }.map { it.await() }

                setupCommentsRecyclerView(commentsWithUserNames)
                updateRatingAndCommentCount(reviews)
            } catch (e: Exception) {
                Toast.makeText(this@SellerDetailActivity, "Error fetching reviews: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupVendorInfo(vendor: VendorData) {
        Glide.with(this)
            .load(R.drawable.seller)
            .placeholder(R.drawable.seller)
            .error(R.drawable.seller_error)
            .circleCrop()
            .into(binding.sellerImage)

        binding.sellerName.text = vendor.name
        binding.sellerEmail.text = vendor.email
        binding.sellerMobile.text = vendor.phoneNumber
        binding.sellerAddress.text = vendor.address
        binding.sellerShopAddress.text = vendor.address
        binding.sellerShopEmail.text = vendor.email

        setupCommentSubmission(vendor.id)
    }

    private fun setupCommentsRecyclerView(comments: List<Comment>) {
        commentsAdapter = CommentsAdapter(comments.toMutableList())
        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SellerDetailActivity)
            adapter = commentsAdapter
        }
        updateCommentVisibility()
    }

    private fun setupCommentSubmission(vendorId: String) {
        binding.submitButton.setOnClickListener {
            val rating = binding.userRating.rating.toInt()
            val comment = binding.commentEditText.text.toString().trim()

            if (rating == 0 && comment.isBlank()) {
                Toast.makeText(this, "Please provide a rating or comment", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val newReview = userRepository.createReview(vendorId, rating, comment)

                    // Fetch user details for the newly submitted review
                    val user = userRepository.getUserDetails(newReview.customerId)
                    val newComment = Comment(newReview.customerId, user.name, newReview.comment, newReview.rating.toFloat(), newReview.createdAt.time)

                    commentsAdapter.addComment(newComment)
                    updateCommentVisibility()
                    fetchVendorReviews(vendorId) // Refresh all reviews
                    Toast.makeText(this@SellerDetailActivity, "Review submitted successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@SellerDetailActivity, "Error submitting review: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // Clear input fields
            binding.userRating.rating = 0f
            binding.commentEditText.text?.clear()
        }
    }

    private fun updateRatingAndCommentCount(reviews: List<Review>) {
        val averageRating = if (reviews.isNotEmpty()) reviews.map { it.rating }.average().toFloat() else 0f
        binding.sellerRating.rating = averageRating
        binding.ratingCount.text = "${reviews.size} ratings"
        binding.commentCount.text = "${reviews.size} comments"
    }

    private fun updateCommentVisibility() {
        val commentCount = commentsAdapter.itemCount
        if (commentCount > 2) {
            if (!isCommentsExpanded) {
                commentsAdapter.limitComments(2)
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
            commentsAdapter.showAllComments()
        } else {
            commentsAdapter.limitComments(2)
        }
        updateCommentVisibility()
    }
}
