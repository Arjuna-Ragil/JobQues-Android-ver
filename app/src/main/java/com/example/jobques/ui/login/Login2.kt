package com.example.jobques.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.jobques.R
import com.example.jobques.ui.home.homeTest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login2 : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var credentialLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login2)

        firebaseAuth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        credentialLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                        handleSignInCredential(credential)
                    } catch (e: ApiException) {
                        handleSignInError(e)
                    }
                } else {
                    Log.e("Login2", "One Tap Sign-in failed with result code: ${result.resultCode}")
                    Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }

        val googleSignInButton: Button = findViewById(R.id.signInGoogleBtn)
        googleSignInButton.setOnClickListener {
            startOneTapSignIn()
        }

        val btn1: ImageButton = findViewById(R.id.loginBack)
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

    private fun handleSignInCredential(credential: SignInCredential) {
        val idToken = credential.googleIdToken
        if (idToken != null) {
            Log.d("Login2", "ID Token: $idToken")
            val authCredential = GoogleAuthProvider.getCredential(idToken, null)
            signInWithFirebase(authCredential)
        } else {
            Log.e("Login2", "ID Token is null")
            Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithFirebase(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login2", "Firebase Sign-In successful")
                    val intent = Intent(this, homeTest::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Login2", "Firebase Sign-In failed", task.exception)
                    Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun handleSignInError(e: ApiException) {
        when (e.statusCode) {
            CommonStatusCodes.CANCELED -> {
                Log.w("Login2", "One Tap Sign-in was canceled by the user.")
            }

            CommonStatusCodes.NETWORK_ERROR -> {
                Log.e("Login2", "Network error during One Tap Sign-in.", e)
                Toast.makeText(this, "Network error during sign-in", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Log.e("Login2", "One Tap Sign-in failed with status code: ${e.statusCode}", e)
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startOneTapSignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                credentialLauncher.launch(intentSenderRequest)
            }
            .addOnFailureListener(this) { e ->
                if (e is ResolvableApiException) {
                    try {
                        e.startResolutionForResult(this, 51)
                    } catch (sendEx: Exception) {
                        Log.e("Login2", "Could not start resolution for One Tap Sign-in.", sendEx)
                    }
                } else {
                    Log.e("Login2", "One Tap Sign-in failed.", e)
                }
            }
    }
}


