package ca.tiffinsp.tiffinserviceapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object Utility {

    // Notification ID.
    private const val NOTIFICATION_ID = 200

    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
        createNotificationChannel(applicationContext)
        val contentIntent = Intent(applicationContext, SplashActivity::class.java)

        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.default_notification_channel_id)
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)

            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel(applicationContext: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channel_name)
            val descriptionText = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(applicationContext.getString(R.string.default_notification_channel_id), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Cancels all notifications.
     *
     */
    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }

}