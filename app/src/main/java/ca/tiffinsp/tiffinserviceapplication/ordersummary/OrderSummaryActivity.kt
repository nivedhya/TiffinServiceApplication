package ca.tiffinsp.tiffinserviceapplication.ordersummary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityForgotPasswordBinding
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityOrderSummaryBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.models.SelectedMenu
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.payment.PaymentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrderSummaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderSummaryBinding
    lateinit var adapter: OrderSummaryAdapter
    private val db = Firebase.firestore

    companion object{
        const val RESTAURANT_DETAILS = "RESTAURANT_DETAILS"
        const val ORDER_ITEMS = "ORDER_ITEMS"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = OrderSummaryAdapter(arrayListOf(), object : OrderSummaryAdapter.OrderSummaryCallback{
            override fun close() {
                onBackPressed()
            }

            override fun updatePrice() {
                this@OrderSummaryActivity.updatePrice()
            }
        })
        val restaurant = intent.getSerializableExtra(RESTAURANT_DETAILS) as Restaurant?
        val items = intent.getSerializableExtra(ORDER_ITEMS) as List<SelectedMenu>?
        if(items != null){
            adapter.setNewItems(items)
        }
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@OrderSummaryActivity.adapter
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.tvRestaurantName.text = restaurant?.name

        updatePrice()

        binding.btnCheckout.setOnClickListener {
            if(restaurant?.docId != null){
                val subscription = Subscription(
                    restaurantId = restaurant.docId!!,
                    restaurantName = restaurant.name,
                    restaurantImage = restaurant.images[0],
                    specialInstruction = binding.etSpecialInstructions.text.toString(),
                   menus = adapter.selectedItems,
                    uid = Firebase.auth.currentUser!!.uid,
                    createdDate = null
                )

                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra(PaymentActivity.SUBSCRIPTION_DETAILS, subscription)
                startActivity(intent)
            }
        }
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }

    fun updatePrice(){
        var total = 0.0
        adapter.selectedItems.forEach {
            total += it.restaurantMenu.price * it.quantity
        }
       binding.btnCheckout.text = "$$total Checkout"
    }
}