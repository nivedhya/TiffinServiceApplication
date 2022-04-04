package ca.tiffinsp.tiffinserviceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityRestaurantBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.models.RestaurantMenu
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.models.User
import ca.tiffinsp.tiffinserviceapplication.tabs.home.BannerSliderAdapter
import ca.tiffinsp.tiffinserviceapplication.tabs.home.ServiceAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class RestaurantActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    lateinit var binding: ActivityRestaurantBinding
    lateinit var adapter: MenuAdapter
    private var restaurantId:String? = null
    private var restaurant:Restaurant? = null

    companion object {
        const val RESTAURANT_DOC_ID = "RESTAURANT_DOC_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MenuAdapter(this, arrayListOf(), object: MenuAdapter.OnMenuCallback{
            override fun onMenuClick(pos: Int) {
                if(adapter.selectedMenuPositions.contains(pos)){
                    adapter.selectedMenuPositions.remove(pos)
                    adapter.notifyItemChanged(pos)
                }else{
                    adapter.selectedMenuPositions.add(pos)
                    adapter.notifyItemChanged(pos)
                }
                if(adapter.selectedMenuPositions.isNotEmpty()){
                    binding.btnProceed.visibility = View.VISIBLE
                }else{
                    binding.btnProceed.visibility = View.GONE
                }
            }
        })
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(this@RestaurantActivity)
            adapter = this@RestaurantActivity.adapter
        }
        restaurantId = intent.getStringExtra(RESTAURANT_DOC_ID)
        if(restaurantId != null){
            getData(restaurantId!!)
        }
        binding.btnProceed.setOnClickListener {
            if(adapter.selectedMenuPositions.isNotEmpty()){
                val arrayList = arrayListOf<RestaurantMenu>()
                adapter.selectedMenuPositions.forEach {
                    arrayList.add(restaurant!!.menu[it])
                }

                val subscription = Subscription(
                    restaurantId = restaurantId!!,
                    restaurantName = restaurant!!.name,
                    restaurantImage = restaurant!!.images[0],
                    menus = arrayList,
                    uid = Firebase.auth.currentUser!!.uid,
                    createdDate = null
                )
                db.collection("subscriptions")
                    .document()
                    .set(subscription.toMap())
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Successfully purchased subscription", Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }

    private fun getData(restaurantId: String) {
        db.collection(FirestoreCollections.RESTAURANTS).document(restaurantId).get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val gson = Gson()
                val restaurantJson = gson.toJson(it.result!!.data)
                restaurant = gson.fromJson(restaurantJson, Restaurant::class.java)
                binding.apply {
                    tvName.text = restaurant!!.name
                    tvDescription.text = restaurant!!.description
                    tvRating.text = "${restaurant!!.rating}"
                    vp.adapter = BannerSliderAdapter(
                        this@RestaurantActivity,
                        restaurant!!.images
                    )
                }
                adapter.setNewItems(restaurant!!.menu)
            }
        }
    }
}