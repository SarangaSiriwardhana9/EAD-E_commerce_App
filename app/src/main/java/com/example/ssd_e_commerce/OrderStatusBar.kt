// OrderStatusBar.kt
package com.example.ssd_e_commerce.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.ViewOrderStatusBarBinding

class OrderStatusBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewOrderStatusBarBinding

    init {
        orientation = HORIZONTAL
        binding = ViewOrderStatusBarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setReviewCount(count: Int) {
        if (count > 0) {
            binding.toReviewBadge.visibility = VISIBLE
            binding.toReviewBadge.text = count.toString()
        } else {
            binding.toReviewBadge.visibility = GONE
        }
    }
}