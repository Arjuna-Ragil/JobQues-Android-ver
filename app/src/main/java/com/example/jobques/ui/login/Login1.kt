package com.example.jobques.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.jobques.R
import com.example.jobques.databinding.Login1Binding
import com.example.jobques.ui.home.homeTest
import com.google.firebase.auth.FirebaseAuth

class Login1 : ComponentActivity() {

    private lateinit var binding: Login1Binding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Login1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val btn1: Button = findViewById(R.id.btnLogin)
        btn1.setOnClickListener {
            val intent = Intent(this, Login2::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent2 = Intent(this, homeTest::class.java)
            startActivity(intent2)
        }
    }
}