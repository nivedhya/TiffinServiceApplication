package ca.tiffinsp.tiffinserviceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ca.tiffinsp.tiffinserviceapplication.onboardingActivity.*
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
                    imageDrawable = R.drawable.subscribe,
            backgroundDrawable = R.color.primary,
            titleColorRes = R.color.black,
            descriptionColorRes = R.color.black,
            backgroundColorRes = R.color.black,
            titleTypefaceFontRes = R.font.poppins_font_family,
            descriptionTypefaceFontRes = R.font.poppins_font_family,
        ))
        addSlide(AppIntroFragment.createInstance(
            title = "Subscribe",
            description = "Select your meal from your selected Tiffin services and subscribe it on monthly basis",
            imageDrawable = R.drawable.ordering,
            backgroundDrawable = R.color.red,
            titleColorRes = R.color.black,
            descriptionColorRes = R.color.black,
            backgroundColorRes = R.color.black,
            titleTypefaceFontRes = R.font.poppins_font_family,
            descriptionTypefaceFontRes = R.font.poppins_font_family,
        ))
        addSlide(AppIntroFragment.createInstance(
            title = "Delivery",
            description = "Get your food delivered daily on your address and enjoy",
            imageDrawable = R.drawable.fooddelivery,
            backgroundDrawable = R.color.orange,
            titleColorRes = R.color.primary,
            descriptionColorRes = R.color.black,
            backgroundColorRes = R.color.black,
            titleTypefaceFontRes = R.font.poppins_font_family,
            descriptionTypefaceFontRes = R.font.poppins_font_family,
        ))

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }
}

