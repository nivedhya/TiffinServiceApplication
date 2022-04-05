package ca.tiffinsp.tiffinserviceapplication.models

import java.io.Serializable

class Restaurant(var docId: String?, var name:String,var description:String, var rating: Double, var images:ArrayList<String>, var menu:List<RestaurantMenu>):Serializable {


    fun toMap(): HashMap<String,Any>{
        return hashMapOf("name" to name,
            "description" to description,
            "rating" to rating,
            "images" to images,
            "menu" to menu.map {
               return it.toMap()
            },
            )
    }

}