package ca.tiffinsp.tiffinserviceapplication


import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivitySubscriptionDetailBinding
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.subscription.SubscriptionMenuAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class SubscriptionDetail : AppCompatActivity() {
    private var daysleft = 0
    private lateinit var binding: ActivitySubscriptionDetailBinding
    private var subscription: Subscription? = null
    lateinit var adapter: SubscriptionMenuAdapter
    private val db = Firebase.firestore

    companion object {
        const val SUBSCRIPTION_DETAILS = "SUBSCRIPTION_DETAILS"
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        subscription = intent.getSerializableExtra(SUBSCRIPTION_DETAILS) as Subscription?
        if(subscription == null){
            return
        }
        binding.tvName.text = subscription!!.restaurantName
        val renewalDate = Calendar.getInstance()
        renewalDate.timeInMillis = subscription!!.renewalDate
        val currentDate = Calendar.getInstance()
        daysleft = ((renewalDate.timeInMillis - currentDate.timeInMillis) / (24 * 60 * 60 * 1000)).toInt()
        binding.percentage.text = "$daysleft \n Days"

        adapter = SubscriptionMenuAdapter(this, subscription!!.menus)
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SubscriptionDetail.adapter
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.DaysDetail.text = "Renewal date : ${dateFormat.format(Date(subscription!!.renewalDate))}"
        binding.buttonCancel.setOnClickListener {
            showCancelDialog();
        }
    }

    private fun showCancelDialog(){
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Cancel Subscription")
            .setMessage("Are you sure you want to cancel this subscription?")
            .setPositiveButton(
                "Yes"
            ) { dialog, _ ->
                db.collection(FirestoreCollections.SUBSCRIPTIONS).document(subscription!!.docId!!)
                    .update(hashMapOf<String, Any>("active" to false))
                dialog.cancel()
                onBackPressed()
                Toast.makeText(baseContext, "Subscription cancelled", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null).create()

        alertDialog.setOnShowListener(OnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.black))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.black))
        })
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        updateProgressBar()
    }

    private fun updateProgressBar() {
        binding.progressbar.progress = daysleft * 100 /30
    }
}
