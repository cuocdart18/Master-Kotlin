package com.example.myapplication.realtime_database.map

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainRtdbBinding
import com.example.myapplication.realtime_database.`object`.MainActivity
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
        val myRef = database.getReference("my_map")

        val mapUser = hashMapOf<String, Boolean>(
            "1" to true,
            "2" to false,
            "3" to true,
            "4" to false
        )
        // push and add listener onCompletion
        myRef.setValue(mapUser) { error, ref ->
            Log.d(TAG, "log: push successfully")
        }
    }

    private fun getDataFromRemote() {
        val database = Firebase.database
        val myRef = database.getReference("my_map")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val mapResult = mutableMapOf<String, Boolean>()
                for (data in dataSnapshot.children) {
                    val key = data.key ?: "no_thing"
                    val value = data.getValue<Boolean>() ?: false
                    mapResult[key] = value
                }

                Log.d(TAG, "Value is: $mapResult")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateDataFromLocal() {
        val database = Firebase.database
        val myRef = database.getReference("my_map")

        val map = mapOf(
            "1" to false,
            "2" to true,
            "3" to false,
            "4" to true
        )

        // update
        myRef.updateChildren(map) { err, ref ->
            Log.d(TAG, "log: update successfully")
        }
    }

    private fun deleteDataFromLocal() {
        val database = Firebase.database
        val myRef = database.getReference("my_map")

        // delete
        myRef.removeValue() { error, ref ->
            Log.d(TAG, "log: delete successfully")
        }
    }
}