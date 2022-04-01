package ca.tiffinsp.tiffinserviceapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderMenuItemBinding
import ca.tiffinsp.tiffinserviceapplication.models.RestaurantMenu
import com.bumptech.glide.Glide

class MenuAdapter(
    var context: Context,
    var menus: ArrayList<RestaurantMenu>,
    var callback: OnMenuCallback
) : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    fun setNewItems(menus: List<RestaurantMenu>){
        this.menus.clear()
        this.menus.addAll(menus)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {

        val binding = ViewholderMenuItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(menus[position])
    }

    override fun getItemCount(): Int {
        return menus.size
    }


    open class MenuHolder(private val binding: ViewholderMenuItemBinding, callback: OnMenuCallback) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnAdd.setOnClickListener {
                if(adapterPosition != -1){
                    callback.onMenuClick(adapterPosition)
                }
            }
        }

        fun bind(menu: RestaurantMenu){

            binding.apply {
                Glide.with(itemView.context).load(menu.image).centerCrop()
                    .into(ivTiffin)
                tvPackageName.text = menu.name
                tvTiffinItems.text = menu.items
                tvDays.text = menu.days
                if(menu.deliveryAvailable){
                    tvTiffinDelivery.visibility  = View.VISIBLE
                }else{
                    tvTiffinDelivery.visibility  = View.GONE
                }
                tvPrice.text = "$${menu.price}"
            }
        }

    }

    interface OnMenuCallback{
        fun onMenuClick(pos: Int)
    }
}

