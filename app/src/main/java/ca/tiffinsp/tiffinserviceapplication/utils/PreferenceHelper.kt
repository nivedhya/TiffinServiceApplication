package ca.tiffinsp.tiffinserviceapplication.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    companion object {
        const val PREF_NAME = "TIFFIN_PREF"
        const val USER_PREF = "USER_PREF"
    }

    fun getPref(context: Context): SharedPreferences{
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getUser(preferences: SharedPreferences){
        val userJson = preferences.getString(USER_PREF, "{}")


    }

}