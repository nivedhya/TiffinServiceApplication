package ca.tiffinsp.tiffinserviceapplication.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import ca.tiffinsp.tiffinserviceapplication.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val email: String = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString().trim { it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(baseContext, "Please Enter Email Address",
                    Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                    if(task.isSuccessful) {
                        Toast.makeText(baseContext, "Email was sent to reset your password.",
                            Toast.LENGTH_LONG).show()

                        finish()
                    }
                    else{
                        Toast.makeText(baseContext, ".exception!!.message.toString()",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            onBackPressed()
        }
    }
}