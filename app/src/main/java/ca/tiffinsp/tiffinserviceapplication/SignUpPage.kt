package ca.tiffinsp.tiffinserviceapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import ca.tiffinsp.tiffinserviceapplication.authentication.ForgotPasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore;
    var name : String="";
    var password: String="";
    var email:String="";
    var gender:String="";
    var dateofbirth:String="";
    var contact:String="";
    var building:String="";
    var streetname:String="";
    var city:String="";
    var province:String="";
    var country:String="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        auth = Firebase.auth


        findViewById<ImageButton>(R.id.BackButtonimage).setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.login).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.Cancel_button).setOnClickListener {
            finish()
        }



        fun validate() : Boolean{
             var isValid =true;
             name=findViewById<EditText>(R.id.PersonName).text.toString();
             gender=findViewById<EditText>(R.id.gender).text.toString();
             dateofbirth=findViewById<EditText>(R.id.dateofbirth).text.toString();
             contact=findViewById<EditText>(R.id.Mobile_TextPhone).text.toString();
             email=findViewById<EditText>(R.id.Emailid_EmailAddress).text.toString();
             password=findViewById<EditText>(R.id.Password_TextPassword).text.toString();
             building=findViewById<EditText>(R.id.building_edittext).text.toString();
             streetname=findViewById<EditText>(R.id.streetname_edittext).text.toString();
             city=findViewById<EditText>(R.id.city_edittext).text.toString();
             province=findViewById<EditText>(R.id.province_edittext).text.toString();
             country=findViewById<EditText>(R.id.country_edittext).text.toString();

            if(name.isEmpty()|| gender.isEmpty() || dateofbirth.isEmpty() || contact.isEmpty()||email.isEmpty()||
                    password.isEmpty()||building.isEmpty()||streetname.isEmpty()||city.isEmpty()||province.isEmpty()||country.isEmpty())
            {
                return false;
            }

            //need to validate email only



            return isValid;
        }
        findViewById<Button>(R.id.Submit_button).setOnClickListener{
            var validated : Boolean =validate();
            if(validated)
            {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                                val userObject = User(name,gender,dateofbirth,contact,email,password,building,streetname,city,province,country);

                            // Add a new document with a generated ID
                            db.collection("users")
                                .add(userObject)
                                .addOnSuccessListener { documentReference ->
                                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

                                //renders to home activity
                                    val intent = Intent(this@SignUpPage, ForgotPasswordActivity::class.java)
                                    startActivity(intent)

                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                }

                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
//                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
//                            updateUI(null)
                        }
                    }

            }
            else
            {
                print("something wrong");
            }

        }
    }
}