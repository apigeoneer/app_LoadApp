package com.gmail.apigeoneer.loadapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

fun NotificationManager.sendNotification(
        messageBody: String,
        applicationContext: Context
) {

    val NOTIFICATION_ID = 0

    // Since building the notification uses the pending intent, the intents come first
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)

    val contentPendingIntent =  PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.download_channel_id)
    )
            .setSmallIcon(R.drawable.load)
            .setContentTitle(applicationContext
                    .getString(R.string.notification_title))
            .setContentText(messageBody)

            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())

}