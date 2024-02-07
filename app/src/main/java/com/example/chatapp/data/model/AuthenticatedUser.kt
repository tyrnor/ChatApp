package com.example.chatapp.data.model

data class AuthenticatedUser(
    val userId: String,
    val email: String?,
    val displayName: String?,
)
