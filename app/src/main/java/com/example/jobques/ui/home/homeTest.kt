package com.example.jobques.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.jobques.R
import com.example.jobques.databinding.HomeBinding
import com.example.jobques.ui.login.Login2
import com.google.firebase.auth.FirebaseAuth

class homeTest : ComponentActivity(){

    private lateinit var binding: HomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val btn1: Button = findViewById(R.id.logoutBtn)
        btn1.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Login2::class.java)
            startActivity(intent)
            finish()
        }
    }
}