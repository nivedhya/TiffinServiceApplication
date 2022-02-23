package ca.tiffinsp.tiffinserviceapplication.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import ca.tiffinsp.tiffinserviceapplication.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            finish()
        }
    }
}