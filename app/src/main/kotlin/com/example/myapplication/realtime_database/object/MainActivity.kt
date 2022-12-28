package com.example.myapplication.realtime_database.`object`

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainRtdbBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainRtdbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRtdbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPushData.setOnClickListener { pushDataFromLocal() }
        binding.btnGetData.setOnClickListener { getDataFromRemote() }
        binding.btnUpdateData.setOnClickListener { updateDataFromLocal() }
        binding.btnDeleteData.setOnClickListener { deleteDataFromLocal() }
    }

    private fun pushDataFromLocal() {
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("user_info")

        val user1 = User(1, "Cuoc Dat", Job(2, "developer"))
        // push and add listener onCompletion
        myRef.setValue(user1) { error, ref ->
            Log.d(TAG, "log: push with ${error?.message}")
        }
    }

    private fun getDataFromRemote() {
        val database = Firebase.database
        val myRef = database.getReference("user_info")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<User>()

                Log.d(TAG, "Value is: ${value.toString()}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateDataFromLocal() {
        val database = Firebase.database
        val myRef = database.getReference("user_info")

        val map = hashMapOf<String, Any?>(
            "name" to "Cuoc Dat dev",
            "job/name" to "Android developer"
        )

        // update
        myRef.updateChildren(map) { err, ref ->
            Log.d(TAG, "log: update successfully")
        }
    }

    private fun deleteDataFromLocal() {
        val database = Firebase.database
        val myRef = database.getReference("user_info")

        // delete
        myRef.removeValue() { error, ref ->
            Log.d(TAG, "log: delete successfully")
        }
    }
}