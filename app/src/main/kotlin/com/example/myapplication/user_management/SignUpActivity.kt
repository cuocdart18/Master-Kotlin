package com.example.myapplication.user_management

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignUpAuthBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val TAG = SignUpActivity::class.java.simpleName
    private lateinit var binding: ActivitySignUpAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "sign up user")
            signUp()
        }
    }

    private fun signUp() {
        // sign up here
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        Log.e(TAG, "email:$email and password:$password")

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // clear all activity before this
                    finishAffinity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "sign up activity is here")
    }
}