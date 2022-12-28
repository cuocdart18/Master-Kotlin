package com.example.myapplication.realtime_database.`object`

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val id: Int? = null, val name: String? = null, val job: Job? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
