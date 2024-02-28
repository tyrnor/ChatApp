package com.example.chatapp.data.model

import java.sql.Timestamp

data class ContactInformation(
    val displayName: String = "",
    val email: String = "",
    val addedAt: Timestamp,
)
