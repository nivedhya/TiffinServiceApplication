package ca.tiffinsp.tiffinserviceapplication



        import android.content.ContentValues.TAG
                import android.os.Bundle
                import android.util.Log
                import androidx.appcompat.app.AppCompatActivity
                import kotlinx.android.synthetic.main.activity_subscription_detail.*
                import java.text.DateFormat
                import java.text.SimpleDateFormat
                import java.time.LocalDate
                import java.time.format.DateTimeFormatter
                import java.util.*
                import java.util.logging.Level.parse
                import kotlin.time.Duration.Companion.parse
                import android.widget.TextView as AndroidWidgetTextView



        class SubscriptionDetail : AppCompatActivity() {
            private var progressVal = 0
            private var daysleft =0
            private var nextSubscription = 0
            override fun onCreate(savedInstanceState: Bundle?) {

                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_subscription_detail)
                val startDate = findViewById<AndroidWidgetTextView>(R.id.startDate);
                val daysRemaining = findViewById<android.widget.TextView>(R.id.DaysDetail);

                val subscribedDate = "2022-05-20"
                startDate.text = subscribedDate
                val Subdate = SimpleDateFormat("yyyy-MM-dd").parse(subscribedDate)
                val today = Date()



                val l = LocalDate.parse(subscribedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                val d =  l.dayOfYear + 30
                val z = l.plusDays(30)



                val a = (today.time + 30)/ ( 1000 * 24 * 3600)
                val diff = today.time - Subdate.time
                val differenceDates = diff
                val seconds = differenceDates / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                val dayDifference = days.toString()
                val daysRemaininginSub = 30 - days
                nextSubscription = daysRemaininginSub.toInt()
                val RdayDifference = daysRemaininginSub.toString()

                if ((daysRemaininginSub < 30)&& (daysRemaininginSub > 0.0) ) {
                    daysRemaining.text = "Renewal date : $z"
                } else {
                    daysRemaining.text = "No Active Subscription"
                }
                daysleft = daysRemaininginSub.toInt()
                progressVal = 100 - (days.toInt()* 3.33).toInt()
            }

            override fun onStart() {
                super.onStart()
                updateProgressBar()
            }
            private fun updateProgressBar() {
                progressbar.progress = progressVal
                percentage.text = "$daysleft Days Remaining"



                if ((nextSubscription < 30)&& (nextSubscription > 0.0) ) {
                    percentage.text = "$daysleft Days Remaining"
                } else {
                    percentage.text = "0%"
                }

            }
        }
