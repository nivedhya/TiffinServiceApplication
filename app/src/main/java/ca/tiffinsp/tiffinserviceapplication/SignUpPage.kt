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


//toast....

        findViewById<Button>(R.id.Submit_button).setOnClickListener{
            var validated : Boolean =validate();
//            alreadyExist();
            if(validated)
            {
//                val check =alreadyExist();
                 auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                                val userObject = User(name,gender,dateofbirth,contact,email,password,building,streetname,city,province,country);

                            // Add a new document with a generated ID
                            db.collection("users")
                                .add(userObject.getData())
                                .addOnSuccessListener { documentReference ->
                                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

                                //renders to home activity
                                    val intent = Intent(this@SignUpPage, TabActivity::class.java)
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
                            Toast.makeText(baseContext, "Authentication failed, User Already exists.",
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
    fun validate() : Boolean{
        Log.d(TAG, "in validate function")
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


        if(name.isEmpty())
        {

            findViewById<EditText>(R.id.PersonName).setError("Invalid Input ");
            isValid=false;
        }
        if(gender.isEmpty())
        {

            findViewById<EditText>(R.id.gender).setError("Invalid Input ");
            isValid=false;
        }
        val pattern = Regex("([0-9]\\/)");
        if(dateofbirth.isEmpty() || pattern.containsMatchIn(dateofbirth)==false)
        {

            findViewById<EditText>(R.id.dateofbirth).setError("Invalid Input ");
            isValid=false;
        }
        val pat2 =Regex("([0-9])");
        if(contact.isEmpty() || pat2.containsMatchIn(contact)==false)
        {

            findViewById<EditText>(R.id.Mobile_TextPhone).setError("Invalid Input ");
            isValid=false;
        }
        val pat3 = Regex(".com$")
        if(email.isEmpty() || pat3.containsMatchIn(email)==false)
        {

            findViewById<EditText>(R.id.Emailid_EmailAddress).setError("Invalid Input ");
            isValid=false;
        }
        if(password.isEmpty())
        {

            findViewById<EditText>(R.id.Password_TextPassword).setError("Invalid Input ");
            isValid=false;
        }
        if(building.isEmpty())
        {

            findViewById<EditText>(R.id.building_edittext).setError("Invalid Input ");
            isValid=false;
        }
        if(streetname.isEmpty())
        {

            findViewById<EditText>(R.id.streetname_edittext).setError("Invalid Input ");
            isValid=false;
        }
        if(city.isEmpty())
        {

            findViewById<EditText>(R.id.city_edittext).setError("Invalid Input ");
            isValid=false;
        }
        if(province.isEmpty())
        {

            findViewById<EditText>(R.id.province_edittext).setError("Invalid Input ");
            isValid=false;
        }
        if(country.isEmpty())
        {

            findViewById<EditText>(R.id.country_edittext).setError("Invalid Input ");
            isValid=false;
        }

        return isValid;
    }
//    fun alreadyExist(): Boolean{
//        Log.d(TAG, "in alreadyexisted function")
//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    if(document.id== "email" )
//                    {
//
//
//                        Toast.makeText(baseContext, "User Already Exists, Please Login",
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//        Toast.makeText(baseContext, "True",
//            Toast.LENGTH_SHORT).show();
//        return true;
//    }
}