package com.example.chatapp.domain.repository

import com.example.chatapp.data.model.UserInformation
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit>
    suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>>
}