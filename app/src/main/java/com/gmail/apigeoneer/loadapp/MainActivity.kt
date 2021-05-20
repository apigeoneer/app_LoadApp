package com.gmail.apigeoneer.loadapp

import android.app.DownloadManager
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import com.gmail.apigeoneer.loadapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOADAPP_URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter.git"
        private const val RETROFIT_URL = "https://github.com/square/retrofit.git"
    }

    // data binding
    private lateinit var binding: ActivityMainBinding
    private var urlSelected = "https://github.com/bumptech/glide.git"       // default: Glide
    private var repoSelected = "Glide repo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // register the download receiver
        registerReceiver(DownloadUtils(this, binding.downloadCv).receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        binding.downloadCv.setOnClickListener {
            DownloadUtils(this, binding.downloadCv).download(urlSelected, repoSelected)
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
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

}