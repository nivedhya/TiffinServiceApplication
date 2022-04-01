package ca.tiffinsp.tiffinserviceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityRestaurantBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.models.User
import ca.tiffinsp.tiffinserviceapplication.tabs.home.BannerSliderAdapter
import ca.tiffinsp.tiffinserviceapplication.tabs.home.ServiceAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class RestaurantActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    lateinit var binding: ActivityRestaurantBinding
    var adapter = MenuAdapter(this, arrayListOf(), object: MenuAdapter.OnMenuCallback{
        override fun onMenuClick(pos: Int) {

        }
    })

    companion object {
        const val RESTAURANT_DOC_ID = "RESTAURANT_DOC_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(this@RestaurantActivity)
            adapter = this@RestaurantActivity.adapter
        }
        val restaurantId = intent.getStringExtra(RESTAURANT_DOC_ID)
        if(restaurantId != null){
            getData(restaurantId)
        }
    }

    private fun getData(restaurantId: String) {
        db.collection(FirestoreCollections.RESTAURANTS).document(restaurantId).get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val gson = Gson()
                val restaurantJson = gson.toJson(it.result!!.data)
                val restaurant = gson.fromJson(restaurantJson, Restaurant::class.java)
                binding.apply {
                    tvName.text = restaurant.name
                    tvDescription.text = restaurant.description
                    tvRating.text = "${restaurant.rating}"
                    vp.adapter = BannerSliderAdapter(
                        this@RestaurantActivity,
                        arrayOf(
                            "https://cdn.pixabay.com/photo/2016/12/26/17/28/spaghetti-1932466__340.jpg",
                            "https://cdn.pixabay.com/photo/2017/12/10/14/47/pizza-3010062__340.jpg",
                            "https://cdn.pixabay.com/photo/2017/02/15/10/39/salad-2068220__340.jpg"
                        )
                    )
                }
                adapter.setNewItems(restaurant.menu)
            }
        }

    }
}