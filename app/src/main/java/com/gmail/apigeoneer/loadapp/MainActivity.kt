package com.gmail.apigeoneer.loadapp

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.gmail.apigeoneer.loadapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    // data binding
    private lateinit var binding: ActivityMainBinding
    private var downloadID: Long = 0
    private var urlSelected = "https://github.com/bumptech/glide.git"       // default: Glide
    private var repoSelected = "Glide repo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.downloadBtn.setOnClickListener {
            download(urlSelected, repoSelected)
        }

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.loadapp_rb ->
                    if (checked) {
                        urlSelected = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter.git"
                        repoSelected = "Load app repo"
                    }
                R.id.retrofit_rb ->
                    if (checked) {
                        urlSelected = "https://github.com/square/retrofit.git"
                        repoSelected = "Retrofit repo"
                    }
            }
        }
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (id == downloadID) {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun download(selectedURL: String, selectedRepo: String) {
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