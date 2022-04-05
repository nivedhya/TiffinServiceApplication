package ca.tiffinsp.tiffinserviceapplication.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import ca.tiffinsp.tiffinserviceapplication.R


object DialogProgress {

    fun build(context: Context): AlertDialog{
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setView(R.layout.progress_dialog)
        return builder.create()
    }

}