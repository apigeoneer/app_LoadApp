package com.gmail.apigeoneer.loadapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    private var fileName = ""
    private var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // receiving values from the notification
        fileName = intent.getStringExtra("fileName").toString()
        status = intent.getStringExtra("status").toString()

        findViewById<TextView>(R.id.file_value_tv).text = fileName
        findViewById<TextView>(R.id.status_value_tv).text = status

        findViewById<Button>(R.id.ok_btn).setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }
    }
}