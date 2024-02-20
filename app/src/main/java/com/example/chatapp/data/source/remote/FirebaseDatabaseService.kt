package com.example.chatapp.data.source.remote

import com.example.chatapp.data.model.UserInformation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDatabaseService {

    private val dbReference = Firebase.database.reference

    fun addUser(uid: String, userInfo: UserInformation) : Task<Void> {
        return dbReference.child("users").child(uid).setValue(userInfo)
    }
}