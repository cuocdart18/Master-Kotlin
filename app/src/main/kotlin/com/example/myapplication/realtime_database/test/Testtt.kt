package com.example.myapplication.realtime_database.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTestttRtdbBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Testtt : AppCompatActivity() {
    private val TAG = Testtt::class.java.simpleName
    private lateinit var binding: ActivityTestttRtdbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestttRtdbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPushData.setOnClickListener { pushDataToRemoteDb() }
        binding.btnGetData.setOnClickListener { getDataFromRemoteDb() }
        binding.btnDeleteData.setOnClickListener { deleteDataFromRemote() }
    }

    private fun pushDataToRemoteDb() {
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")
        val value = binding.edtValueFromLocal.text.toString().trim()
        // push
        myRef.setValue(value)

        // practice
        database.getReference("test1").setValue("em cua ngay hom qua")
        database.getReference("message/music").setValue("chac ai do se ve")
    }

    private fun getDataFromRemoteDb() {
        readDataFromRemoteDb()
    }

    private fun deleteDataFromRemote() {
        val database = Firebase.database
        database.getReference("test1").removeValue { error, ref ->
            Log.d(TAG, "Log: ${error?.message}}")
        }
    }

    private fun readDataFromRemoteDb() {
        val database = Firebase.database
        val myRef = database.getReference("message")
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
                // update UI
                binding.tvValueFromRtdb.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}