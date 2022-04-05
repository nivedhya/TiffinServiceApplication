package ca.tiffinsp.tiffinserviceapplication.models

import java.io.Serializable

class SelectedMenu(val restaurantMenu: RestaurantMenu, var quantity:Int):Serializable {



    fun toMap(): HashMap<String,Any>{
        return hashMapOf("restaurantMenu" to restaurantMenu.toMap(),
            "quantity" to quantity,
        )
    }
}