package com.example.chatapp.data.repository

import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.data.source.remote.FirebaseAuthService
import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuthService: FirebaseAuthService) : AuthRepository{
    override suspend fun register(email: String, password: String): Result<AuthenticatedUser> {
        return firebaseAuthService.register(email, password)
    }

    override suspend fun login(email: String, password: String): Result<AuthenticatedUser> {
        return firebaseAuthService.login(email, password)
    }

    override suspend fun googleSignIn(token: String): Result<AuthenticatedUser> {
        return firebaseAuthService.googleSignIn(token)
    }

    override suspend fun updateUserProfile(userId: String, displayName: String): Result<Unit> {
        return firebaseAuthService.updateUserProfile(userId, displayName)
    }
}