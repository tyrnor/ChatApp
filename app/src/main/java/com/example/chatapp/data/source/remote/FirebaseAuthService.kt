package com.example.chatapp.data.source.remote

import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.data.model.AuthenticationException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
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
        } catch (e: FirebaseAuthException) {
            Result.failure(AuthenticationException(e.errorCode, e.message))
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
        } catch (e: FirebaseAuthException) {
            Result.failure(AuthenticationException(e.errorCode, e.message))
        } catch (e: Exception) {
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
        } catch (e: FirebaseAuthException) {
            Result.failure(AuthenticationException(e.errorCode, e.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(userId: String, displayName: String): Result<Unit> {
        return try {
            val userProfileChangeRequest = userProfileChangeRequest {
                this.displayName = displayName
            }
            Firebase.auth.currentUser?.updateProfile(userProfileChangeRequest)?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}