package ca.tiffinsp.tiffinserviceapplication.models

import com.google.firebase.firestore.FieldValue
import com.google.firestore.v1.DocumentTransform
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Subscription(var restaurantId: String, var restaurantName:String, var restaurantImage:String,  var menus: ArrayList<RestaurantMenu>, var uid: String, var createdDate:FirebaseDate?) {


    fun toMap(): HashMap<String,Any>{
        return hashMapOf(
            "restaurantId" to restaurantId,
            "restaurantImage" to restaurantImage,
            "restaurantName" to restaurantName,
            "menus" to menus.map { menu -> menu.toMap() },
            "uid" to uid,
            "createdDate" to FieldValue.serverTimestamp()
        )
    }

}