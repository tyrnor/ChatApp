package com.example.chatapp.data.source.remote

import com.example.chatapp.data.model.ContactInformation
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.common.FirestoreUtils
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
        val searchName = FirestoreUtils.SEARCH_NAME_PATTERN.replace(userInfo.displayName, "")
            .lowercase(Locale.getDefault())

        val userInfoWithSearchName = userInfo.copy(searchName = searchName)
        return try {
            db.collection("users").document(uid).set(userInfoWithSearchName).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addContact(
        uid: String,
        cid: String,
        contactInformation: ContactInformation,
    ): Result<Unit> {
        return try {
            db.collection("users").document(uid).collection("contacts").document(cid)
                .set(contactInformation).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserById(uid: String) : Result<UserInformation> {
        return try {
            val documentSnapshot = db.collection("users").document(uid).get().await()
            val user = documentSnapshot.toObject(UserInformation::class.java) ?: UserInformation()
            Result.success(user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>> =
        flow {
            val formattedQuery =
                FirestoreUtils.SEARCH_NAME_PATTERN.replace(query, "").lowercase(Locale.getDefault())
            val snapshot = db.collection("users")
                .orderBy("searchName")
                .whereGreaterThanOrEqualTo("searchName", formattedQuery)
                .whereLessThanOrEqualTo("searchName", formattedQuery + '\uf8ff')
                .limit(6)
                .get()
                .await()
            val users = snapshot.documents.map { document ->
                document.toObject(UserInformation::class.java)?.copy(uid = document.id) ?: UserInformation()
            }
            emit(Result.success(users))
        }.catch { e ->
            emit(Result.failure(e))
        }


}