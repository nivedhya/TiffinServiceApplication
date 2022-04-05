package ca.tiffinsp.tiffinserviceapplication.tabs.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.R
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderHomePageBinding
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderMenuItemBinding
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import com.bumptech.glide.Glide


class ServiceAdapter(
    var context: Context,
    var subscriptions: ArrayList<Subscription>
) : RecyclerView.Adapter<ServiceAdapter.ServiceHolder>() {

    fun addNewData(subscriptions: ArrayList<Subscription>){
        this.subscriptions.clear()
        this.subscriptions.addAll(subscriptions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHolder {
        val binding = ViewholderHomePageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ServiceHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceHolder, position: Int) {
        holder.bind(subscriptions[position])
    }

    override fun getItemCount(): Int {
        return subscriptions.size
    }


    class ServiceHolder(val binding: ViewholderHomePageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subscription: Subscription){
            binding.apply {
                tvTiffinName.text = subscription.restaurantName
                var menu = subscription.menus[0].restaurantMenu
                if(menu.isVeg){
                    tvTiffinVeg.text = "Veg"
                }else{
                    tvTiffinVeg.text = "Non-Veg"
                }
                tvTiffinDelivery.text = menu.days


                Glide.with(binding.root.context).load(subscription.restaurantImage).centerCrop()
                    .into(ivTiffin)
            }

        }

    }
}