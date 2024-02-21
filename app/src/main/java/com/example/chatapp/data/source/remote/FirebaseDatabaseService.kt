package com.example.chatapp.data.source.remote

import com.example.chatapp.data.model.UserInformation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class FirebaseDatabaseService {

    private val db = Firebase.firestore

    suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit> {
        return try {
            db.collection("users").document(uid).set(userInfo).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>> =
        flow {
            val snapshot = db.collection("users")
                .orderBy("displayName")
                .startAt(query)
                .endAt(query + '\uf8ff')
                .get()
                .await()
            val users = snapshot.toObjects(UserInformation::class.java)
            emit(Result.success(users))
        }.catch { e ->
            emit(Result.failure(e))
        }
}