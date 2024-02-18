package com.example.chatapp.domain.repository

import com.example.chatapp.data.model.AuthenticatedUser

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<AuthenticatedUser>
    suspend fun login(email: String, password: String): Result<AuthenticatedUser>
    suspend fun googleSignIn(token: String): Result<AuthenticatedUser>
    suspend fun updateUserProfile(userId: String, displayName: String): Result<Unit>
}