package com.gmail.apigeoneer.loadapp

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.gmail.apigeoneer.loadapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var repoSelected = ""
    private var status = ""
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        notificationManager = ContextCompat.getSystemService(
            this, NotificationManager::class.java
        ) as NotificationManager

        handleNotification()

        navigateBackToMain()
    }

    private fun handleNotification() {
        // Receive values from the notification
        repoSelected = intent.getStringExtra("repoSelected").toString()
        status = intent.getStringExtra("status").toString()

        binding.fileValueTv.text = repoSelected
        binding.statusValueTv.text = status

        if (status == "Download successful") {
            binding.statusValueTv.setTextColor(resources.getColor(R.color.green))
        } else {
            binding.statusValueTv.setTextColor(resources.getColor(R.color.red))
        }

        // Cancel the notification after the action button is clicked
        notificationManager.cancelNotifications()
    }

    private fun navigateBackToMain() {
        binding.okBtn.setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }
    }
}