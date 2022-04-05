package ca.tiffinsp.tiffinserviceapplication.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.tiffinsp.tiffinserviceapplication.LoginPage
import ca.tiffinsp.tiffinserviceapplication.R
import ca.tiffinsp.tiffinserviceapplication.TabActivity
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivityPaymentBinding
import ca.tiffinsp.tiffinserviceapplication.databinding.SearchrestaurantPageBinding
import ca.tiffinsp.tiffinserviceapplication.models.Restaurant
import ca.tiffinsp.tiffinserviceapplication.models.SelectedMenu
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.ordersummary.OrderSummaryActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {

    companion object{
        const val SUBSCRIPTION_DETAILS = "SUBSCRIPTION_DETAILS"
    }
    lateinit var binding: ActivityPaymentBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val subscription = intent.getSerializableExtra(SUBSCRIPTION_DETAILS) as Subscription?

        binding.btnCheckout.setOnClickListener {
            if(subscription != null){
                db.collection("subscriptions")
                    .document()
                    .set(subscription.toMap())
                    .addOnSuccessListener { _ ->
                        Toast.makeText(this, "Successfully subscribed", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, TabActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
            }

        }
    }
}