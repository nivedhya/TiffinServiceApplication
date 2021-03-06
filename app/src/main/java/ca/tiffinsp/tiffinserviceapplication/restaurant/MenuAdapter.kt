package ca.tiffinsp.tiffinserviceapplication.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.R
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderMenuItemBinding
import ca.tiffinsp.tiffinserviceapplication.models.RestaurantMenu
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer

class MenuAdapter(
    var context: Context,
    var menus: ArrayList<RestaurantMenu>,
    var callback: OnMenuCallback
) : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    val selectedMenuPositions = arrayListOf<Int>()

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
        if(selectedMenuPositions.contains(position)){
            holder.binding.btnAdd.text = "Remove"
            holder.binding.btnAdd.backgroundTintList = holder.binding.root.context.getColorStateList(
                R.color.nav_inactive
            )
        }else{
            holder.binding.btnAdd.text = "+ Add"
            holder.binding.btnAdd.backgroundTintList = holder.binding.root.context.getColorStateList(
                R.color.primary
            )
        }
    }

    override fun getItemCount(): Int {
        return menus.size
    }


    open class MenuHolder(val binding: ViewholderMenuItemBinding, callback: OnMenuCallback) : RecyclerView.ViewHolder(binding.root) {
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
                ivTiffin.setOnClickListener {
                    StfalconImageViewer.Builder<String>(itemView.context, arrayListOf(menu.image)) { view, image ->
                        Glide.with(itemView.context).load(image).into(view)
                    }.show()
                }
                tvPackageName.text = menu.name
                tvTiffinItems.text = menu.items
                tvDays.text = menu.days
                if(menu.isVeg){
                    tvTiffinDelivery.text = "Veg"
                }else{
                    tvTiffinDelivery.text = "Non-Veg"
                }
                tvPrice.text = "$${menu.price}"
            }
        }

    }

    interface OnMenuCallback{
        fun onMenuClick(pos: Int)
    }
}

