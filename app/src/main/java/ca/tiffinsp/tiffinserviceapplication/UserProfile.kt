package ca.tiffinsp.tiffinserviceapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    val db = Firebase.firestore;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        // get data from thr firestore.....
        var user  = User("aman","male","1998/07/10","6378769032","a@gmail.com","1234456","342",
        "brentwood drive","brampton","ON","CA");

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






        findViewById<Button>(R.id.button2).setOnClickListener{
            user.name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString();
            user.gender = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString();
            user.dateofbirth = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString();
            user.contact = findViewById<EditText>(R.id.editTextTextPersonName4).text.toString();
            user.email = findViewById<EditText>(R.id.editTextTextPersonName5).text.toString();

            //to update the document of "users" collections.
            // Use "user" object for this...
            //.........

        }

//        Log.d(TAG, "in alreadyexisted function")
//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
////                    if(document.id== "email" )
////                    {
////
////
////                        Toast.makeText(baseContext, "User Already Exists, Please Login",
////                                Toast.LENGTH_SHORT).show();
////
////                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//        Toast.makeText(baseContext, "True",
//            Toast.LENGTH_SHORT).show();




    }
}
