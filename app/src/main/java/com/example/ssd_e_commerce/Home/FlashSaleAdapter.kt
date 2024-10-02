package com.example.ssd_e_commerce.Home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ssd_e_commerce.R

class FlashSaleAdapter(private val items: List<FlashSaleItem>) : RecyclerView.Adapter<FlashSaleAdapter.FlashSaleViewHolder>() {

    inner class FlashSaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.flashSaleItemImage)
        val nameTextView: TextView = itemView.findViewById(R.id.flashSaleItemName)
        val salePriceTextView: TextView = itemView.findViewById(R.id.flashSaleItemSalePrice)
        val originalPriceTextView: TextView = itemView.findViewById(R.id.flashSaleItemOriginalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashSaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flash_sale, parent, false)
        return FlashSaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashSaleViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResourceId)
        holder.nameTextView.text = item.name
        holder.salePriceTextView.text = "$${item.salePrice}"
        holder.originalPriceTextView.apply {
            text = "$${item.originalPrice}"
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun getItemCount(): Int = items.size
}