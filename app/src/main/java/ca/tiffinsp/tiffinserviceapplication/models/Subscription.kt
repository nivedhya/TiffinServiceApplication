package ca.tiffinsp.tiffinserviceapplication.models

import com.google.firebase.firestore.FieldValue
import com.google.firestore.v1.DocumentTransform
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Subscription(var docId:String? = null, var restaurantId: String, var restaurantName:String, var restaurantImage:String,  var menus: ArrayList<SelectedMenu>,var specialInstruction:String, var uid: String, var createdDate:Long, var renewalDate:Long, var active:Boolean = true):Serializable {


    fun toMap(): HashMap<String,Any>{
        return hashMapOf(
            "restaurantId" to restaurantId,
            "restaurantImage" to restaurantImage,
            "restaurantName" to restaurantName,
            "specialInstruction" to specialInstruction,
            "active" to active,
            "menus" to menus.map { menu -> menu.toMap() },
            "uid" to uid,
            "renewalDate" to renewalDate,
            "createdDate" to createdDate
        )
    }

}