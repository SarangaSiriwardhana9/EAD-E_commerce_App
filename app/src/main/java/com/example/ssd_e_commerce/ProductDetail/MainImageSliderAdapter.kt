package com.example.ssd_e_commerce.ProductDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.databinding.SlideProductImageBinding

class MainImageSliderAdapter(private val images: List<Any>) : RecyclerView.Adapter<MainImageSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(private val binding: SlideProductImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Any) {
            when (image) {
                is Int -> binding.imageView.setImageResource(image)
                is String -> Glide.with(binding.root.context).load(image).into(binding.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = SlideProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size
}
