package com.gmail.apigeoneer.loadapp

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.gmail.apigeoneer.loadapp.customView.ButtonState
import com.gmail.apigeoneer.loadapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val GLIDE_URL = "https://github.com/bumptech/glide.git"
        private const val LOADAPP_URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter.git"
        private const val RETROFIT_URL = "https://github.com/square/retrofit.git"
    }

    // data binding
    private lateinit var binding: ActivityMainBinding

    private lateinit var notificationManager: NotificationManager
    private var urlSelected = ""
    private var repoSelected = ""

    private var downloadID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        notificationManager = ContextCompat.getSystemService(
                this, NotificationManager::class.java
        ) as NotificationManager

        binding.downloadCv.setOnClickListener {

            if (urlSelected != "" && repoSelected != "") {
                download(urlSelected, repoSelected)
                Log.d(TAG, "::::::::::::::::: $repoSelected ::::::::::::::::::")
                Log.d(TAG, "::::::::::::::::: $urlSelected ::::::::::::::::::")
            } else {
                Toast.makeText(this, "Select a repo to start downloading", Toast.LENGTH_LONG)
                    .show()
            }
        }

        // Register the download receiver
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        createChannel(
                getString(R.string.download_channel_id),
                getString(R.string.download_notification_channel_name))
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.glide_rb ->
                    if (checked) {
                        urlSelected = GLIDE_URL
                        repoSelected = "Glide repo"
                    }
                R.id.loadapp_rb ->
                    if (checked) {
                        urlSelected = LOADAPP_URL
                        repoSelected = "Load app repo"
                    }
                R.id.retrofit_rb ->
                    if (checked) {
                        urlSelected = RETROFIT_URL
                        repoSelected = "Retrofit repo"
                    }
            }
        }
    }

    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "You download was successful!"

            notificationManager.createNotificationChannel(notificationChannel)
        } else {
            Log.d(TAG, "VERSION.SDK_INT < O")
            Toast.makeText(this, "VERSION.SDK_INT < O", Toast.LENGTH_SHORT).show()
        }
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            val action = intent?.action

            //Log.d(TAG, "::::::::::::::: $repoSelected ::::::::::::::::::")

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
                            binding.downloadCv.setButtonState(ButtonState.Completed)
                        }
                    }
                }
            }
        }
    }

    private fun download (selectedURL: String, selectedRepo: String) {
        // set the download button to the loading state
        binding.downloadCv.setButtonState(ButtonState.Loading)

        val request =
            DownloadManager.Request(Uri.parse(selectedURL))
                .setTitle(selectedRepo)
                .setDescription("Downloading $selectedRepo from internet")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)        // enqueue puts the download request in the queue
    }

}


