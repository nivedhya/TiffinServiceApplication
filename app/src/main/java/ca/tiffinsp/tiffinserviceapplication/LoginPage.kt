package ca.tiffinsp.tiffinserviceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import ca.tiffinsp.tiffinserviceapplication.authentication.ForgotPasswordActivity

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this@LoginPage, TabActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.signUp).setOnClickListener {
            val intent = Intent(this@LoginPage, SignUpPage::class.java)
            startActivity(intent)
        }


        findViewById<TextView>(R.id.forgotPassword).setOnClickListener {
            val intent = Intent(this@LoginPage, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}