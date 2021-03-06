package ca.tiffinsp.tiffinserviceapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.edit
import ca.tiffinsp.tiffinserviceapplication.authentication.ForgotPasswordActivity
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore;

    //EditText email;
    var input_email: String = "";
    var input_password: String = "";
    var showPassword = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        auth = Firebase.auth

        val pref = PreferenceHelper().getPref(context = applicationContext)
        var email = pref.getString(PreferenceHelper.REMEMBER_EMAIL, "")
        findViewById<EditText>(R.id.emailField).setText(email)


        findViewById<Button>(R.id.button).setOnClickListener {
            val email_view: EditText = findViewById(R.id.emailField);
            val password_view: EditText = findViewById(R.id.password);
            input_email = email_view.text.toString();
            input_password = password_view.text.toString();

            if (validate(input_email, input_password)) {
                if(findViewById<AppCompatCheckBox>(R.id.cb_remember_me).isChecked){
                    pref.edit {
                        putString(PreferenceHelper.REMEMBER_EMAIL, input_email)
                    }
                }else{
                    pref.edit {
                        remove(PreferenceHelper.REMEMBER_EMAIL)
                    }                }
                auth.signInWithEmailAndPassword(input_email, input_password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser!!.uid
                            db.collection(FirestoreCollections.USERS).document(uid).get()
                                .addOnCompleteListener {
                                    if (it.isSuccessful && it.result != null) {
                                        val gson = Gson()
                                        val userJson = gson.toJson(it.result!!.data)
                                        pref.edit {
                                            putString(PreferenceHelper.USER_PREF, userJson)
                                        }
                                        Toast.makeText(
                                            baseContext, "Sign in successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this@LoginPage, TabActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            //  updateUI(null)
                        }

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
        val editTextPassword = findViewById<EditText>(R.id.password)
        editTextPassword.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= editTextPassword.getRight() - editTextPassword.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {
                    val cursorStart = editTextPassword.selectionStart
                    val cursorEnd = editTextPassword.selectionEnd
                    if(showPassword){
                        editTextPassword.transformationMethod = PasswordTransformationMethod()
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_black_24dp, 0);
                    }else{
                        editTextPassword.transformationMethod = null
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black_24dp, 0);
                    }
                    showPassword = !showPassword
                    editTextPassword.setSelection(cursorStart, cursorEnd)
                    return@OnTouchListener true
                }
            }
            false
        })

    }

    fun validate(email: String, password: String): Boolean {
        var isValid = true
        if (email.isEmpty()) {
            isValid = false
        }
        if (password.isEmpty()) {
            isValid = false
        }
        return isValid
    }
}


