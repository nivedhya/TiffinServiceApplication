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




    }

    interface RestaurantCallback{
        fun onRestaurantClick(pos: Int)
    }
}

