package com.example.myapplication.realtime_database.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainRtdbListBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainRtdbListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRtdbListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPushUser.setOnClickListener {
            val id = binding.edtIdUser.text.toString().trim().toInt()
            val name = binding.edtNameUser.text.toString().trim()
            val user = User(id, name)
            pushUserFromLocal(user)
        }
        binding.btnPushListUser.setOnClickListener { pushListUserFromLocal() }
        binding.btnGetListUser.setOnClickListener { getListUserFromRemote() }
        binding.btnGetListUserVerEventListener.setOnClickListener { getListUserFromRemoteVerEventListener() }
    }

    private fun pushUserFromLocal(user: User) {
        val database = Firebase.database
        val myRef = database.getReference("list_user")

        val pathObj = user.id.toString()
        myRef.child(pathObj).setValue(user) { err, ref ->
            Log.d(TAG, "log: update successfully")
        }
    }

    private fun pushListUserFromLocal() {
        val database = Firebase.database
        val myRef = database.getReference("list_user_v2")

        val users = mutableListOf(
            User(0, "Cuoc Dat"),
            User(1, "Son Tung"),
            User(2, "Phuong Ly"),
            User(3, "Suni Ha Long")
        )

        myRef.setValue(users) { err, ref ->
            Log.d(TAG, "log: update successfully")
        }
    }

    private fun getListUserFromRemote() {
        val users = mutableListOf<User>()
        val adapter = ListUserAdapter(users)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rcvListUser.adapter = adapter
        binding.rcvListUser.layoutManager = linearLayoutManager

        // init ref to db
        val database = Firebase.database
        val myRef = database.getReference("list_user_v2")

        // query
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()

                for (userSnapShot in snapshot.children) {
                    val user = userSnapShot.getValue<User>()
                    user?.let {
                        users.add(user)
                    }
                }
                adapter.setUsersData(users)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "log: update failed")
            }

        })
    }

    private fun getListUserFromRemoteVerEventListener() {
        val users = mutableListOf<User>()
        val adapter = ListUserAdapter(users)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rcvListUser.adapter = adapter
        binding.rcvListUser.layoutManager = linearLayoutManager

        // init ref to db
        val database = Firebase.database
        val myRef = database.getReference("list_user_v2")

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)

                val user = dataSnapshot.getValue<User>()
                user?.let {
                    users.add(user)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")

                val position = dataSnapshot.key?.toInt()
                val user = dataSnapshot.getValue<User>()
                position?.let {
                    user?.let {
                        users[position] = user
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)

                val user = dataSnapshot.getValue<User>()
                user?.let {
                    users.remove(user)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "onCancelled", databaseError.toException())
            }
        }

        myRef.addChildEventListener(childEventListener)
    }
}