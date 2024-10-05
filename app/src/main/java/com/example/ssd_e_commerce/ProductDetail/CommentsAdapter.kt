package com.example.ssd_e_commerce.ProductDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ssd_e_commerce.data.Comment
import com.example.ssd_e_commerce.databinding.ItemCommentBinding
import java.text.SimpleDateFormat
import java.util.*

class CommentsAdapter(private val allComments: MutableList<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    private var visibleComments: List<Comment> = allComments

    class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.commentUserName.text = comment.userName
            binding.commentContent.text = comment.content
            binding.commentRating.rating = comment.rating
            binding.commentTimestamp.text = formatTimestamp(comment.timestamp)
        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(visibleComments[position])
    }

    override fun getItemCount() = visibleComments.size

    fun addComment(comment: Comment) {
        allComments.add(0, comment)
        limitComments(visibleComments.size)
    }

    fun limitComments(limit: Int) {
        visibleComments = allComments.take(limit)
        notifyDataSetChanged()
    }

    fun showAllComments() {
        visibleComments = allComments
        notifyDataSetChanged()
    }
}