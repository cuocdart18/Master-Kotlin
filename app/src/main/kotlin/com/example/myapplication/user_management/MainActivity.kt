package com.example.myapplication.user_management

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMainAuthBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainAuthBinding
    private val MY_REQUEST_CODE = 10

    private lateinit var uri: Uri
    private val activityResultLaucher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                intent?.let {
                    uri = intent.data!!
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    binding.imvAvatarUser.setImageBitmap(bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showUserInformation()

        binding.btnSignOut.setOnClickListener { signOut() }
        binding.imvAvatarUser.setOnClickListener { updateAvatar() }
        binding.btnUpdateProfile.setOnClickListener { updateProfile() }
        binding.btnUpdateEmail.setOnClickListener { updateEmail() }
        binding.btnUpdatePassword.setOnClickListener { updatePassword() }
        binding.btnVerifyEmail.setOnClickListener { verifyEmail() }
        binding.btnDeleteUser.setOnClickListener { deleteUser() }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "activity main is here")
    }

    private fun signOut() {
        Firebase.auth.signOut()
        // navigate to sign in
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showUserInformation() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // update UI
            name?.let {
                binding.tvNameUser.text = name
            }
            binding.tvEmailUser.text = email
            photoUrl?.let {
                Glide.with(this)
                    .load(photoUrl)
                    .into(binding.imvAvatarUser)
            }
        }
    }

    private fun updateAvatar() {
        requestPermission()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery()
            return
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            val permissions = listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                .toTypedArray()
            this.requestPermissions(permissions, MY_REQUEST_CODE)
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLaucher.launch(Intent.createChooser(intent, "Select pictures"))
    }

    private fun updateProfile() {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = binding.edtFullNameUser.text.toString().trim()
            photoUri = uri
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    binding.edtFullNameUser.text = null
                    showUserInformation()
                } else {
                    Log.d(TAG, "Log: ${task.exception}")
                }
            }
    }

    private fun updateEmail() {
        val user = Firebase.auth.currentUser

        val newEmail = binding.edtEmailUser.text.toString().trim()

        user!!.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                    binding.edtEmailUser.text = null
                    showUserInformation()
                } else {
                    Log.d(TAG, "Log: ${task.exception}")
                }
            }
    }

    private fun updatePassword() {
        val user = Firebase.auth.currentUser
        val newPassword = binding.edtPasswordUser.text.toString().trim()

        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    binding.edtPasswordUser.text = null
                } else {
                    Log.d(TAG, "Log: ${task.exception}")
                }
            }
    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                } else {
                    Log.d(TAG, "Log: ${task.exception}")
                }
            }
    }

    private fun deleteUser() {
        val user = Firebase.auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                    finish()
                } else {
                    Log.d(TAG, "Log: ${task.exception}")
                }
            }
    }

    private fun reAuthenticate() {
        val user = Firebase.auth.currentUser!!

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider
            .getCredential("user@example.com", "password1234")

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User re-authenticated.")
                } else {
                    Log.d(TAG, "User authenticate fail")
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (MY_REQUEST_CODE == requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission granted")
                openGallery()
            } else {
                Log.d(TAG, "permission denied")
            }
        }
    }
}