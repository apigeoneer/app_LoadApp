package com.gmail.apigeoneer.loadapp

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.gmail.apigeoneer.loadapp.customView.ButtonState
import com.gmail.apigeoneer.loadapp.customView.LoadingButton

// need to create a class, since need to pass context
// since Can't access getSystemService() from o/s an activity w/o context
class DownloadUtils (
    private val context: Context,
    private val loadingButton: LoadingButton,
    private val notificationManager: NotificationManager,
    private val repoSelected: String
) {
        companion object {
            private const val TAG = "DownloadUtils"
        }

        private var downloadID: Long = 0

        val receiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                val action = intent?.action

                if (id == downloadID) {
                    if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                         /**
                          * get the download status & pass it to the notification
                          * (so that it could then send it to the detail activity)
                          */
                         val query = DownloadManager.Query()
                         query.setFilterById(id)
                         val manager = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                         val cursor = manager.query(query)

                         if (cursor.moveToFirst()) {
                             if (cursor.count > 0) {
                                 val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                                 if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                     notificationManager.sendNotification(
                                             repoSelected,
                                             context,
                                     "Download successful")
                                 } else {
                                     notificationManager.sendNotification(
                                             repoSelected,
                                             context,
                                     "Download failed")
                                 }
                                 loadingButton.setButtonState(ButtonState.Completed)
                             }
                         }
                    }
                }
            }
        }

        fun download(selectedURL: String, selectedRepo: String) {
            // set the download button to the loading state
            loadingButton.setButtonState(ButtonState.Loading)

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