package com.example.ssd_e_commerce.Home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssd_e_commerce.R

class CategoryAdapter(private val categories: List<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.categoryImage)
        val textView: TextView = itemView.findViewById(R.id.categoryName)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val category = categories[position]
                    val context = itemView.context
                    val intent = Intent(context, CategoryProductsActivity::class.java)
                    intent.putExtra("CATEGORY", category.name)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.imageView.setImageResource(category.imageResourceId)
        holder.textView.text = category.name
    }

    override fun getItemCount(): Int = categories.size
}