package ca.tiffinsp.tiffinserviceapplication.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceHelper {

    companion object {
        const val PREF_NAME = "TIFFIN_PREF"
        const val ON_BOARDING_SHOWN_PREF = "ON_BOARDING_SHOWN_PREF"
        const val USER_PREF = "USER_PREF"
        const val REMEMBER_EMAIL = "REMEMBER_EMAIL"
    }

    fun getPref(context: Context): SharedPreferences{
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun deleteUserPref(context: Context){
        getPref(context).edit {
            remove(USER_PREF)
        }
    }



}