package com.example.ssd_e_commerce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// The adapter class extends RecyclerView.Adapter and uses a custom ViewHolder
class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewHolder class to hold references to the views for each data item
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        val nameTextView: TextView = itemView.findViewById(R.id.itemName)
        val priceTextView: TextView = itemView.findViewById(R.id.itemPrice)
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Inflate the layout for a single item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResourceId)
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "$${item.price}"
    }

    // Returns the total number of items in the data set
    override fun getItemCount(): Int = items.size
}