package com.example.ssd_e_commerce.Seller

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.ProductDetail.CommentsAdapter
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.data.Comment
import com.example.ssd_e_commerce.data.Seller
import com.example.ssd_e_commerce.databinding.ActivitySellerDetailBinding

class SellerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellerDetailBinding
    private lateinit var commentsAdapter: CommentsAdapter
    private var seller: Seller? = null
    private var isCommentsExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seller = intent.getSerializableExtra("SELLER") as? Seller
        seller?.let {
            setupSellerInfo(it)
            setupCommentsRecyclerView(it.comments)
            setupCommentSubmission(it)
        }

        binding.backButton.setOnClickListener { finish() }
        binding.viewAllComments.setOnClickListener { toggleCommentVisibility() }
    }

    private fun setupSellerInfo(seller: Seller) {
        Glide.with(this)
            .load(seller.image)
            .placeholder(R.drawable.seller_placeholder)
            .error(R.drawable.seller_error)
            .circleCrop()
            .into(binding.sellerImage)

        binding.sellerName.text = seller.name
        binding.sellerEmail.text = seller.email
        binding.sellerMobile.text = seller.mobileNo
        binding.sellerAddress.text = seller.address
        binding.sellerShopAddress.text = seller.shopAddress
        binding.sellerShopEmail.text = seller.shopEmail
        updateRatingAndCommentCount(seller)
    }

    private fun setupCommentsRecyclerView(comments: List<Comment>) {
        commentsAdapter = CommentsAdapter(comments.filter { it.content.isNotBlank() }.toMutableList())
        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SellerDetailActivity)
            adapter = commentsAdapter
        }
        updateCommentVisibility()
    }

    private fun setupCommentSubmission(seller: Seller) {
        binding.submitButton.setOnClickListener {
            val rating = binding.userRating.rating
            val comment = binding.commentEditText.text.toString().trim()

            if (rating == 0f && comment.isBlank()) {
                Toast.makeText(this, "Please provide a rating or comment", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (comment.isNotBlank()) {
                val newComment = Comment(
                    userId = "current_user_id", // Replace with actual user ID
                    userName = "Current User", // Replace with actual user name
                    content = comment,
                    rating = rating,
                    timestamp = System.currentTimeMillis()
                )
                commentsAdapter.addComment(newComment)
                updateCommentVisibility()
            }

            // Update seller's rating
            val newRatingSum = seller.rating * seller.comments.size + rating
            val newRatingCount = seller.comments.size + 1
            seller.rating = newRatingSum / newRatingCount

            // Update UI
            updateRatingAndCommentCount(seller)

            // Clear input fields
            binding.userRating.rating = 0f
            binding.commentEditText.text?.clear()


            Toast.makeText(this, "Submission successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateRatingAndCommentCount(seller: Seller) {
        binding.sellerRating.rating = seller.rating
        binding.ratingCount.text = "${seller.comments.size} ratings"
        val commentCount = seller.comments.count { it.content.isNotBlank() }
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