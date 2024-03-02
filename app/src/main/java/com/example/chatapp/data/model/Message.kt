package com.example.chatapp.data.model

import com.google.firebase.Timestamp


data class Message(
    val senderId: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
