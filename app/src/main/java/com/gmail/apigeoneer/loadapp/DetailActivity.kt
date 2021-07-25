package com.gmail.apigeoneer.loadapp

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    private var fileName = ""
    private var status = ""
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        notificationManager = ContextCompat.getSystemService(
            this, NotificationManager::class.java
        ) as NotificationManager

        handleNotification()

        navigateBackToMain()
    }

    private fun handleNotification() {
        // Receive values from the notification
        fileName = intent.getStringExtra("fileName").toString()
        status = intent.getStringExtra("status").toString()

        // Cancel the notification after the action button is clicked
        notificationManager.cancelNotifications()

        findViewById<TextView>(R.id.file_value_tv).text = fileName
        findViewById<TextView>(R.id.status_value_tv).text = status
    }

    private fun navigateBackToMain() {
        findViewById<Button>(R.id.ok_btn).setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }
    }
}