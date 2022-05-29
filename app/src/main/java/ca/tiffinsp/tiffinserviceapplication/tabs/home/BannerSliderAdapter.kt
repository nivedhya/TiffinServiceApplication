package ca.tiffinsp.tiffinserviceapplication.tabs.home

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.R
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer


class BannerSliderAdapter(
    var context: Context,
    var images: ArrayList<String>
) : RecyclerView.Adapter<BannerSliderAdapter.BannerHolder>() {
    private var mLayoutInflater: LayoutInflater? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addNewData(images: ArrayList<String>){
        this.images = images
        notifyDataSetChanged()
    }

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.banner_slider, parent, false)
        return BannerHolder(view)
    }

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        Glide.with(context).load(images[position]).centerCrop()
//            .placeholder(R.drawable.loading_spinner)
            .into(holder.imageView);
        holder.imageView.setOnClickListener {
            StfalconImageViewer.Builder<String>(context, images) { view, image ->
                Glide.with(context).load(image).into(view)
            }   .withStartPosition(position)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }


    class BannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_banner)

    }
}