package ca.tiffinsp.tiffinserviceapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.tiffinsp.tiffinserviceapplication.databinding.ActivitySubscriptionDetailBinding
import ca.tiffinsp.tiffinserviceapplication.models.Subscription
import ca.tiffinsp.tiffinserviceapplication.subscription.SubscriptionMenuAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class SubscriptionDetail : AppCompatActivity() {
    private var daysleft = 0
    private lateinit var binding: ActivitySubscriptionDetailBinding
    private var subscription: Subscription? = null
    lateinit var adapter: SubscriptionMenuAdapter

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

    }

    override fun onStart() {
        super.onStart()
        updateProgressBar()
    }

    private fun updateProgressBar() {
        binding.progressbar.progress = daysleft * 100 /30
    }
}
