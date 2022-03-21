package ca.tiffinsp.tiffinserviceapplication

import android.widget.EditText

class User(var name:String,var gender:String,var dateofbirth:String,var contact:String, var email:String,var password:String
,var building:String, var streetname:String,var city:String,var province:String,var country:String) {


    fun getData(): HashMap<String,Any>{
        return hashMapOf("name" to name,
        "gender" to gender,
        "dateofbirth" to dateofbirth,
        "contact" to contact,
        "email" to email,
        "password" to password,
        "building" to building,
        "streetname" to streetname,
        "city" to city,
        "province" to province,
        "country" to country)
    }

}