package ca.tiffinsp.tiffinserviceapplication.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderSearchBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import com.bumptech.glide.Glide

class SearchAdapter(
    var restaurants: ArrayList<Restaurant>,
    var callback: RestaurantCallback
) : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    fun setNewItems(menus: List<Restaurant>){
        this.restaurants.clear()
        this.restaurants.addAll(menus)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {

        val binding = ViewholderSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }


    open class SearchHolder(val binding: ViewholderSearchBinding, callback: RestaurantCallback) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if(adapterPosition != -1){
                    callback.onRestaurantClick(adapterPosition)
                }
            }
        }

        fun bind(restaurant: Restaurant){
            binding.apply {
                tvRestaurant.text = restaurant.name
                Glide.with(itemView.context).load(restaurant.images[0]).centerCrop()
                    .into(ivRestaurant)
                tvRating.text = restaurant.rating.toString()
            }
        }

    }

    interface RestaurantCallback{
        fun onRestaurantClick(pos: Int)
    }
}

