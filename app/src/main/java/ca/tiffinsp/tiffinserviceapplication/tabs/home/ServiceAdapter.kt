package ca.tiffinsp.tiffinserviceapplication.tabs.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.R
import com.bumptech.glide.Glide


class ServiceAdapter(
    var context: Context,
    var images: Array<String>
) : RecyclerView.Adapter<ServiceAdapter.ServiceHolder>() {
    private var mLayoutInflater: LayoutInflater? = null

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.viewholder_home_page, parent, false)
        return ServiceHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceHolder, position: Int) {
        Glide.with(context).load(images[position]).centerCrop()
//            .placeholder(R.drawable.loading_spinner)
            .into(holder.imageView);
    }

    override fun getItemCount(): Int {
        return images.size
    }


    class ServiceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_tiffin)

    }
}