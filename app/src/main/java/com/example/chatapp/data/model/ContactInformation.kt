package com.example.chatapp.data.model

import com.google.firebase.Timestamp


data class ContactInformation(
    val displayName: String = "",
    val email: String = "",
    val addedAt: Timestamp,
)
