package ca.tiffinsp.tiffinserviceapplication.models

class RestaurantMenu(var name:String, var image:String, var items:String,var isVeg:Boolean,var days:String, var price:Double) {


    fun toMap(): HashMap<String,Any>{
        return hashMapOf("name" to name,
            "items" to items,
            "image" to image,
            "isVeg" to isVeg,
            "days" to days,
            "price" to price
        )
    }

}