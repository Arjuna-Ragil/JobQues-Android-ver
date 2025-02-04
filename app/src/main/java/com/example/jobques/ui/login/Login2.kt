package com.example.jobques.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.jobques.R

class Login2 : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login2)

        val btn1: Button = findViewById(R.id.loginBack)
        btn1.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btn2: Button = findViewById(R.id.signInBtn1)
        btn2.setOnClickListener {
            val intent = Intent(this, signin::class.java)
            startActivity(intent)
        }

        val btn3: Button = findViewById(R.id.signUpBtn1)
        btn3.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
    }
}