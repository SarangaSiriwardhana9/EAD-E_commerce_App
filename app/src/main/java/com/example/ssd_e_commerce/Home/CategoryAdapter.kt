package com.example.ssd_e_commerce.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<CategoryItem>,
    private val onCategoryClick: (String, String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryItem) {
            binding.categoryName.text = category.name

            // Load image using Glide
            Glide.with(binding.root.context)
                .load(getCategoryImageResource(category.name.toLowerCase()))
                .placeholder(R.drawable.placeholder_image)
                .into(binding.categoryImage)

            binding.root.setOnClickListener {
                onCategoryClick(category.id, category.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    private fun getCategoryImageResource(categoryName: String): Int {
        return when (categoryName) {
            "clothing" -> R.drawable.clothing
            "toys" -> R.drawable.toys
            "electronic" -> R.drawable.electronic
            "food" -> R.drawable.food
            "gifts" -> R.drawable.gifts
            else -> R.drawable.placeholder_image
        }
    }
}

data class CategoryItem(
    val id: String,
    val name: String,
    val description: String,
    val status: String
)