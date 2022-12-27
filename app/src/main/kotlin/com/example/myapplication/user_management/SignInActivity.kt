package com.example.myapplication.user_management

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignInAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private val TAG = SignInActivity::class.java.simpleName
    private lateinit var binding: ActivitySignInAuthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener { signIn() }
        binding.btnSignUp.setOnClickListener { signUp() }
        binding.btnForgotPassword.setOnClickListener { forgotPassword() }
    }

    private fun signIn() {
        Log.d(TAG, "sign in to main activity")
        // sign in here
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }

    private fun signUp() {
        Log.d(TAG, "sign up to sign up activity")
        // sign up here
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun forgotPassword() {
        val emailAddress = binding.edtEmail.text.toString().trim()

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                } else {
                    Log.d(TAG, "Log: ${task.exception}")
                }
            }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "sign in activity is here")
    }
}