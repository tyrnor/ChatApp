package com.example.chatapp.data.source.remote

import com.example.chatapp.data.model.AuthenticatedUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService {

    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
     suspend fun register(email: String, password: String): Result<AuthenticatedUser> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            Result.success(
                AuthenticatedUser(
                    userId = firebaseUser!!.uid,
                    email = firebaseUser.email,
                    displayName = firebaseUser.displayName
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

     suspend fun login(email: String, password: String): Result<AuthenticatedUser> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            Result.success(
                AuthenticatedUser(
                    userId = firebaseUser!!.uid,
                    email = firebaseUser.email,
                    displayName = firebaseUser.displayName
                )
            )
        } catch (e: Exception){
            Result.failure(e)
        }

    }

    suspend fun googleSignIn(token: String): Result<AuthenticatedUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user
            Result.success(
                AuthenticatedUser(
                    userId = firebaseUser!!.uid,
                    email = firebaseUser.email,
                    displayName = firebaseUser.displayName
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}