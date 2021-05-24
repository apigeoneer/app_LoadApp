package com.gmail.apigeoneer.loadapp

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

// need to create a class, since need to pass context
// since Can't access getSystemService() from o/s an activity w/o context
class DownloadUtils (
        private val context: Context,
        private val downloadButton: LoadingButton,
        private val notificationManager: NotificationManager
     //   private val contentIntent: Intent
) {


}