package ca.tiffinsp.tiffinserviceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = object: CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                val pref = PreferenceHelper().getPref(applicationContext)
                val onBoardingShown = pref.getBoolean(PreferenceHelper.ON_BOARDING_SHOWN_PREF, false)
                if (onBoardingShown) {
                    val userJson = pref.getString(PreferenceHelper.USER_PREF, null)
                    if(userJson.isNullOrEmpty()){
                        val intent = Intent(this@SplashActivity, LoginPage::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this@SplashActivity, TabActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val intent = Intent(this@SplashActivity, onboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        timer.start()
    }
}