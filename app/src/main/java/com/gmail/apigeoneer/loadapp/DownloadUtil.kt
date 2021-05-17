package com.gmail.apigeoneer.loadapp

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

// need to create a class, since need to pass context
// since Can't access getSystemService() from o/s an activity w/o context
class DownloadUtil (private val context: Context) {

     private var downloadID: Long = 0

     val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (id == downloadID) {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun download(selectedURL: String, selectedRepo: String) {
        val request =
                DownloadManager.Request(Uri.parse(selectedURL))
                        .setTitle(selectedRepo)
                        .setDescription("Downloading $selectedRepo from internet")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)        // enqueue puts the download request in the queue
    }
}