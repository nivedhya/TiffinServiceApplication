package ca.tiffinsp.tiffinserviceapplication

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.edit
import ca.tiffinsp.tiffinserviceapplication.models.User
import ca.tiffinsp.tiffinserviceapplication.utils.PreferenceHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class UserProfile : AppCompatActivity() {
    private val db = Firebase.firestore;
    private val auth = Firebase.auth
    val uid = auth.currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        db.collection(FirestoreCollections.USERS).document(uid).get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val gson = Gson()
                val userJson = gson.toJson(it.result!!.data)
                val user = gson.fromJson(userJson, User::class.java)
                val username = findViewById<EditText>(R.id.editTextTextPersonName);
                username.setText(user.name);

                val gender = findViewById<EditText>(R.id.editTextTextPersonName2);
                gender.setText(user.gender);


                val DOB = findViewById<EditText>(R.id.editTextTextPersonName3);
                DOB.setText(user.dateofbirth);

                val contact = findViewById<EditText>(R.id.editTextTextPersonName4);
                contact.setText(user.contact);

                val email = findViewById<EditText>(R.id.editTextTextPersonName5);
                email.setText(user.email);

            }
        }

        findViewById<ImageButton>(R.id.imageButton2).setOnClickListener {
            finish()
        }


        findViewById<Button>(R.id.button2).setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            val gender = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            val dateofbirth = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
            val contact = findViewById<EditText>(R.id.editTextTextPersonName4).text.toString()
            val email = findViewById<EditText>(R.id.editTextTextPersonName5).text.toString()

            if (validate()) {
                val map = hashMapOf<String, Any>(
                    "name" to name,
                    "gender" to gender,
                    "dateofbirth" to dateofbirth,
                    "contact" to contact,
                    "email" to email,
                )
                db.collection(FirestoreCollections.USERS).document(uid).set(
                    map
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        db.collection(FirestoreCollections.USERS).document(uid).get()
                            .addOnCompleteListener { user ->
                                if (user.isSuccessful && user.result != null) {
                                    val gson = Gson()
                                    val pref =
                                        PreferenceHelper().getPref(context = applicationContext)
                                    val userJson = gson.toJson(user.result!!.data)
                                    pref.edit {
                                        putString(PreferenceHelper.USER_PREF, userJson)
                                    }
                                    Toast.makeText(
                                        baseContext, "User profile updated successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        baseContext, "User profile updated failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            baseContext, "User profile updated failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            //to update the document of "users" collections.
            // Use "user" object for this...
            //.........

        }

    }

    fun validate(): Boolean {
        return true
    }
}
