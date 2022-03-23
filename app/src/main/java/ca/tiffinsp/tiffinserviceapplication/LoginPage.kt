package ca.tiffinsp.tiffinserviceapplication
import android.widget.Toast;
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ca.tiffinsp.tiffinserviceapplication.authentication.ForgotPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText

import android.view.View;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore;
    //EditText email;
    var input_email: String = "";
    var input_password:String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        auth = Firebase.auth




        //    Button  button = (Button) findViewById(R.id.button);
        //toast....
        findViewById<Button>(R.id.button).setOnClickListener {

            val email_view: EditText = findViewById(R.id.emailField);
            val password_view :EditText= findViewById(R.id.password);
            input_email =email_view.text.toString();
            input_password = password_view.text.toString();
            Log.d(TAG, input_email)
            Log.d(TAG, input_password)
            auth.signInWithEmailAndPassword(input_email , input_password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        //val user = auth.currentUser
                        Toast.makeText(baseContext, "It Worked",
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginPage, TabActivity::class.java)
                        startActivity(intent)
                      //  updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                      //  updateUI(null)
                    }



                }
        }

            findViewById<TextView>(R.id.signUp).setOnClickListener {
                val intent = Intent(this@LoginPage, SignUpPage::class.java)
                startActivity(intent)
            }


            findViewById<TextView>(R.id.forgotPassword).setOnClickListener {
                val intent = Intent(this@LoginPage, ForgotPasswordActivity::class.java)
                startActivity(intent)


            }





            //Firebase.auth.signOut()


        }
}


  /*  private fun updateUI(user: FirebaseUser?) {
        val currentUser = auth.currentUser;


    }*/

 /*override fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            if (currentUser != null) {
                reload();
            }*/



