package ca.tiffinsp.tiffinserviceapplication.subscription

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.R
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderMenuItemBinding
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderSubscribedItemsBinding
import ca.tiffinsp.tiffinserviceapplication.models.RestaurantMenu
import ca.tiffinsp.tiffinserviceapplication.models.SelectedMenu
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SubscriptionMenuAdapter(
    var context: Context,
    var menus: ArrayList<SelectedMenu>,
) : RecyclerView.Adapter<SubscriptionMenuAdapter.SubscriptionMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionMenuViewHolder {
        val binding = ViewholderSubscribedItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return SubscriptionMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriptionMenuViewHolder, position: Int) {
        holder.bind(menus[position].restaurantMenu)
    }

    override fun getItemCount(): Int {
        return menus.size
    }


    open class SubscriptionMenuViewHolder(val binding: ViewholderSubscribedItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: RestaurantMenu){
            binding.apply {
                Glide.with(itemView.context).load(menu.image).centerCrop()
                    .transform(CenterCrop(), RoundedCorners(10))
                    .into(ivSubscribedItem)
                tvSubscribedItem.text = menu.name
                tvSubscribedItemContent.text = menu.items
            }
        }

    }
}

