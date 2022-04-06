package ca.tiffinsp.tiffinserviceapplication.ordersummary


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.databinding.ViewholderOrderSummaryItemBinding
import ca.tiffinsp.tiffinserviceapplication.models.SelectedMenu

class OrderSummaryAdapter(
    var selectedItems: ArrayList<SelectedMenu>,
    val callback: OrderSummaryCallback
) : RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryHolder>() {

    fun setNewItems(menus: List<SelectedMenu>){
        this.selectedItems.clear()
        this.selectedItems.addAll(menus)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryHolder {

        val binding = ViewholderOrderSummaryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderSummaryHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderSummaryHolder, position: Int) {
        val item = selectedItems[position];
        holder.bind(item)
        holder.binding.apply {
            tvAdd.setOnClickListener {
                item.quantity = item.quantity+1
                tvQuantity.text = item.quantity.toString()
                callback.updatePrice()
            }

            tvReduce.setOnClickListener {
          }
        }
    }

    override fun getItemCount(): Int {
        return selectedItems.size
    }


    open class OrderSummaryHolder(val binding: ViewholderOrderSummaryItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(selectedMenu: SelectedMenu){
            binding.apply {
                tvMenuName.text = selectedMenu.restaurantMenu.name
                tvPrice.text = "$${selectedMenu.restaurantMenu.price}"
                tvQuantity.text = selectedMenu.quantity.toString()

            }
        }
    }

    interface OrderSummaryCallback{
        fun close()
        fun updatePrice()
    }
}

