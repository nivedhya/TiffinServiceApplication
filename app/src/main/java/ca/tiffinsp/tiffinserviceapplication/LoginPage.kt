package ca.tiffinsp.tiffinserviceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this@LoginPage, TabActivity::class.java)
            startActivity(intent)
        }
    }
}