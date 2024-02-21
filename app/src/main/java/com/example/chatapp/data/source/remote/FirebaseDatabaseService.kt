package com.example.chatapp.data.source.remote

import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.data.utils.FirestoreUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Locale


class FirebaseDatabaseService {

    private val db = Firebase.firestore

    suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit> {
        val searchName = FirestoreUtils.SEARCH_NAME_PATTERN.replace(userInfo.displayName, "").lowercase(Locale.getDefault())

        val userInfoWithSearchName = userInfo.copy(searchName = searchName)
        return try {
            db.collection("users").document(uid).set(userInfoWithSearchName).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>> =
        flow {
            val formattedQuery = FirestoreUtils.SEARCH_NAME_PATTERN.replace(query, "").lowercase(Locale.getDefault())
            val snapshot = db.collection("users")
                .whereGreaterThanOrEqualTo("searchName", formattedQuery)
                .whereLessThanOrEqualTo("searchName",formattedQuery + '\uf8ff')
                .get()
                .await()
            val users = snapshot.toObjects(UserInformation::class.java)
            emit(Result.success(users))
        }.catch { e ->
            emit(Result.failure(e))
        }
}