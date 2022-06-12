package ca.tiffinsp.tiffinserviceapplication.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import ca.tiffinsp.tiffinserviceapplication.LoginPage
import ca.tiffinsp.tiffinserviceapplication.R
import ca.tiffinsp.tiffinserviceapplication.TabActivity
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityPaymentBinding
import ca.tiffinsp.tiffinserviceapplication.databinding.SearchrestaurantPageBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.models.SelectedMenu
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.models.User
import ca.tiffinsp.tiffinserviceapplication.ordersummary.OrderSummaryActivity
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RetryPolicy
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethod
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.view.CardInputWidget
import org.json.JSONObject
import java.util.HashMap

class PaymentActivity : AppCompatActivity() {

    companion object {
        const val SUBSCRIPTION_DETAILS = "SUBSCRIPTION_DETAILS"
    }

    lateinit var binding: ActivityPaymentBinding
    private val db = Firebase.firestore
    private lateinit var stripe: Stripe
    var subscription: Subscription? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        VolleyLog.DEBUG = true;

        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PaymentConfiguration.init(
            context = applicationContext,
            publishableKey = "pk_test_51L73WFFE2q43FvBCfe3EbNfzLB638tVZkqxNmvbh04M3CoCgqOlBXxK6rTETwFU8PI3WQmiIu5X1bJmPioW7h7lX00QJNp3W7l"
        )
        subscription = intent.getSerializableExtra(SUBSCRIPTION_DETAILS) as Subscription?

        binding.backButton.setOnClickListener {
         onBackPressed()
        }

        binding.tvAmount.text = "Total amount: \$${subscription!!.total}"

        binding.btnCheckout.setOnClickListener {
            if (subscription != null) {
                pay()
            }

        }
    }


    private fun pay() {
        println("Pay invoked")
        val cardInputWidget =
            findViewById<CardInputWidget>(R.id.cardInputWidget)
        val params = cardInputWidget.paymentMethodCreateParams
        if (params == null) {
            Toast.makeText(applicationContext, "Please input the details", Toast.LENGTH_SHORT)
                .show()
            return
        }
        stripe = Stripe(
            applicationContext,
            PaymentConfiguration.getInstance(applicationContext).publishableKey
        )
        stripe.createPaymentMethod(params, callback = object : ApiResultCallback<PaymentMethod> {
            override fun onSuccess(result: PaymentMethod) {
                if (result.id != null) {
                    sendPaymentDetails(result.id!!)
                }
            }

            override fun onError(e: Exception) {
                Toast.makeText(applicationContext, "Payment Failed", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun sendPaymentDetails(paymentMethod: String) {
        val userJson = PreferenceHelper().getPref(applicationContext)
            .getString(PreferenceHelper.USER_PREF, "{}");
        val user = Gson().fromJson(userJson, User::class.java)

        val queue = Volley.newRequestQueue(this)
        val params: MutableMap<String, Any> = HashMap()
        params["amount"] = subscription!!.total
        params["paymentMethod"] = paymentMethod
        params["email"] = user.email
        params["name"] = user.name

        val stringRequest = JsonObjectRequest(
            Request.Method.POST,
            "https://light-principled-fabrosaurus.glitch.me/subscribe",
            JSONObject(
                params as Map<*, *>?
            ),
            { responseJson ->
                val success = responseJson.optBoolean("success", false)
                println(responseJson)
                if (success) {
                    db.collection("subscriptions")
                        .document()
                        .set(subscription!!.toMap())
                        .addOnSuccessListener { _ ->
                            Toast.makeText(this, "Successfully subscribed", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, TabActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }

                } else {
                    Toast.makeText(this, "Payment failed.", Toast.LENGTH_SHORT).show()
                }

            },
            {
                Toast.makeText(
                    this,
                    "Payment failed. Please try again after some time",
                    Toast.LENGTH_SHORT
                ).show()
            }).setRetryPolicy(
            DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        queue.add(stringRequest)
    }


}