package com.example.jobques

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class Login1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login1)

        val btn1: Button = findViewById(R.id.btnLogin)
        btn1.setOnClickListener {
            val intent = Intent(this, Login2::class.java)
            startActivity(intent)
        }
    }
}