package com.example.chatapp.data.source.remote

import android.util.Log
import com.example.chatapp.data.model.UserInformation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseDatabaseService {

    private val db = Firebase.firestore

    suspend fun addUser(uid: String, userInfo: UserInformation) : Result<Unit> {
        return try {
            db.collection("users").document(uid).set(userInfo).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}