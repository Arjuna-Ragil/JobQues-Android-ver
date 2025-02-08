package com.example.jobques.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.jobques.R
import com.example.jobques.databinding.SigninBinding
import com.example.jobques.ui.home.homeTest
import com.google.firebase.auth.FirebaseAuth

class signin : ComponentActivity() {

    private lateinit var binding: SigninBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInBtn2.setOnClickListener {
            val email = binding.usernameOrEmail.text.toString()
            val password = binding.password2.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, homeTest::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
            }


        val btn1: ImageButton = findViewById(R.id.loginBack2)
        btn1.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.switchSignUp.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
            finish()
        }
    }
}