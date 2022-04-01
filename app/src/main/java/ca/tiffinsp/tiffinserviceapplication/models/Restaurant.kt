package ca.tiffinsp.tiffinserviceapplication.models

class Restaurant(var name:String,var description:String, var rating: Double, var images:ArrayList<String>, var menu:List<RestaurantMenu>) {


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