package ca.tiffinsp.tiffinserviceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class SignUpPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        findViewById<ImageButton>(R.id.BackButtonimage).setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.login).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.Cancel_button).setOnClickListener {
            finish()
        }
    }
}