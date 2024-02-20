package com.example.chatapp.domain.repository

import com.example.chatapp.data.model.UserInformation

interface DatabaseRepository {
    suspend fun addUser(uid: String, userInfo: UserInformation) : Result<Unit>
}