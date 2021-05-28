package com.gmail.apigeoneer.loadapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
        messageBody: String,
        applicationContext: Context,
        status: String
) {
    // Since building the notification uses the pending intent, the intents come first
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    // passing values to the Detail Fragment
    contentIntent.apply {
        putExtra("filename", messageBody)
        putExtra("status", status)
    }

    val contentPendingIntent =  PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val loadImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.load
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(loadImage)
            .bigLargeIcon(null)

    // Build the notification
    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.download_channel_id)
    )
            .setPriority(NotificationCompat.PRIORITY_HIGH)

            .setSmallIcon(R.drawable.load)
            .setContentTitle(applicationContext
                    .getString(R.string.notification_title))
            .setContentText(messageBody)

            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)                      // delete the notification after it has been clicked

            .setStyle(bigPicStyle)
            .setLargeIcon(loadImage)

            .addAction(
                    R.drawable.load,
                    "View details",
                    contentPendingIntent
            )

    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}