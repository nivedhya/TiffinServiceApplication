package ca.tiffinsp.tiffinserviceapplication.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import ca.tiffinsp.tiffinserviceapplication.FirestoreCollections
import ca.tiffinsp.tiffinserviceapplication.restaurant.RestaurantActivity
import ca.tiffinsp.tiffinserviceapplication.databinding.SearchrestaurantPageBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.utils.DialogProgress
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {
    lateinit var binding: SearchrestaurantPageBinding
    lateinit var adapter: SearchAdapter
    private val db = Firebase.firestore
    val restaurants = arrayListOf<Restaurant>()
    var restaurantFiltered:List<Restaurant> = arrayListOf()
    lateinit var progressDialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchrestaurantPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = DialogProgress.build(this)

        adapter = SearchAdapter(arrayListOf(), object: SearchAdapter.RestaurantCallback{
            override fun onRestaurantClick(pos: Int) {
                var restaurant = restaurantFiltered[pos]
                if(restaurant.docId != null){
                    val intent = Intent(this@SearchActivity, RestaurantActivity::class.java)
                    intent.putExtra(RestaurantActivity.RESTAURANT_DOC_ID, restaurant.docId)
                    startActivity(intent)
                }

            }
        })

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.searchField.addTextChangedListener { editable ->
            val text = editable.toString().toLowerCase().trim()
            if(text.isNotEmpty()){
                restaurantFiltered = restaurants.filter {
                    it.name.toLowerCase().contains(text)
                }.toList()
            }else{
                restaurantFiltered = arrayListOf()
            }

            adapter.setNewItems(restaurantFiltered)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SearchActivity.adapter
        }
        getData()
    }


    private fun getData() {
        progressDialog.show()
        db.collection(FirestoreCollections.RESTAURANTS).get().addOnCompleteListener {
        }
    }

}