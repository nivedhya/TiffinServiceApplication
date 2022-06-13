package ca.tiffinsp.tiffinserviceapplication.ordersummary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.tiffinsp.tiffinserviceapplication.TabActivity
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityOrderSummaryBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.models.SelectedMenu
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.payment.PaymentActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.json.JSONObject
import java.util.*

class OrderSummaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderSummaryBinding
    lateinit var adapter: OrderSummaryAdapter
    private val db = Firebase.firestore
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String
    private var subscription:Subscription? = null
    var total = 0.0

    companion object{
        const val RESTAURANT_DETAILS = "RESTAURANT_DETAILS"
        const val ORDER_ITEMS = "ORDER_ITEMS"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
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
            val renewalDate = Calendar.getInstance()
            renewalDate.add(Calendar.DATE, 30)
            if(restaurant?.docId != null){
                subscription = Subscription(
                    restaurantId = restaurant.docId!!,
                    restaurantName = restaurant.name,
                    restaurantImage = restaurant.images[0],
                    specialInstruction = binding.etSpecialInstructions.text.toString(),
                    menus = adapter.selectedItems,
                    uid = Firebase.auth.currentUser!!.uid,
                    total = total,
                    renewalDate = renewalDate.timeInMillis,
                    createdDate = Calendar.getInstance().timeInMillis
                )

                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra(PaymentActivity.SUBSCRIPTION_DETAILS, subscription)
                startActivity(intent)
//                initializePayment()
            }
        }
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }

    fun updatePrice(){
        total = 0.0
        adapter.selectedItems.forEach {
            total += it.restaurantMenu.price * it.quantity
        }
       binding.btnCheckout.text = "$$total Checkout"
    }


    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Merchant name",
                customer = customerConfig,
                allowsDelayedPaymentMethods = true
            )
        )
    }

    private fun initializePayment(){
        val queue = Volley.newRequestQueue(this)
        val params: MutableMap<String, Any> = HashMap()
        params["amount"] = total


        val stringRequest = JsonObjectRequest(
            Request.Method.POST, "https://light-principled-fabrosaurus.glitch.me/checkout", JSONObject(
                params as Map<*, *>?
            ),
            { responseJson ->
                println(responseJson)
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(this, publishableKey)
                presentPaymentSheet()
            },
            {
                Toast.makeText(this, "Payment failed. Please try again after some time", Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this, "Payment failed. Please try again after some time", Toast.LENGTH_SHORT).show()
                print("Error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                db.collection("subscriptions")
                    .document()
                    .set(subscription!!.toMap())
                    .addOnSuccessListener { _ ->
                        Toast.makeText(this, "Successfully subscribed", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, TabActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                print("Completed")
            }
        }
    }
}