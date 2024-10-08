import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ssd_e_commerce.Notification
import com.example.ssd_e_commerce.R
import com.example.ssd_e_commerce.databinding.OrderItemBinding

class NotificationAdapter(private val notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(private val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.productNameTextView.text = notification.product.name
            binding.quantityTextView.text = "Qty: ${notification.quantity}"
            binding.priceTextView.text = "Order ${notification.status}"

            // Load image using Glide
            Glide.with(binding.root.context)
                .load(notification.product.images.first())
                .into(binding.productImageView)

            // Set background color based on status
            val backgroundColor = when (notification.status.toLowerCase()) {
                "packed" -> R.color.light_background
                "shipped" -> R.color.light_background
                "delivered" -> R.color.light_background
                else -> R.color.white
            }
            binding.root.setBackgroundResource(backgroundColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size
}
