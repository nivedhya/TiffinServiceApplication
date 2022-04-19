package ca.tiffinsp.tiffinserviceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import ca.tiffinsp.tiffinserviceapplication.onboardingActivity.*
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType


class onboardingActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!
        isColorTransitionsEnabled = true
        setTransformer(AppIntroPageTransformerType.SlideOver)


// You can customize your parallax parameters in the constructors.
        setTransformer(AppIntroPageTransformerType.Parallax(
            titleParallaxFactor = 1.0,
            imageParallaxFactor = -1.0,
            descriptionParallaxFactor = 2.0
        ))
        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(AppIntroFragment.createInstance(
            title = "Search Tiffin Services",
            description = "Homely food daily!..Let's get started! You can compare different restaurants and choose your favourites according to your taste",
            imageDrawable = R.drawable.onboarding_1,
            backgroundDrawable = R.color.primary,
            titleColorRes = R.color.white,
            descriptionColorRes = R.color.white,
            backgroundColorRes = R.color.primary,
            titleTypefaceFontRes = R.font.poppins_font_family,
            descriptionTypefaceFontRes = R.font.poppins_font_family,
        ))
        addSlide(AppIntroFragment.createInstance(
            title = "Subscribe",
            description = "Select your meal from your selected Tiffin services and subscribe it on monthly basis",
            imageDrawable = R.drawable.onboarding_2,
            backgroundDrawable = R.color.red,
            titleColorRes = R.color.white,
            descriptionColorRes = R.color.white,
            backgroundColorRes = R.color.red,
            titleTypefaceFontRes = R.font.poppins_font_family,
            descriptionTypefaceFontRes = R.font.poppins_font_family,
        ))
        addSlide(AppIntroFragment.createInstance(
            title = "Delivery",
            description = "Get your food delivered daily on your address and enjoy",
            imageDrawable = R.drawable.onboarding_3,
            backgroundDrawable = R.color.orange,
            titleColorRes = R.color.white,
            descriptionColorRes = R.color.white,
            backgroundColorRes = R.color.orange,
            titleTypefaceFontRes = R.font.poppins_font_family,
            descriptionTypefaceFontRes = R.font.poppins_font_family,
        ))

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        handleOnBoardingFinish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        handleOnBoardingFinish()
    }
    private fun handleOnBoardingFinish(){
        val pref = PreferenceHelper().getPref(applicationContext)
        pref.edit {
            this.putBoolean(PreferenceHelper.ON_BOARDING_SHOWN_PREF, true)
        }
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }
}

