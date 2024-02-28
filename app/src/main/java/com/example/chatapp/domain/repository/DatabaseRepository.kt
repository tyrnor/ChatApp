package com.example.chatapp.domain.repository

import com.example.chatapp.data.model.ContactInformation
import com.example.chatapp.data.model.UserInformation
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit>
    suspend fun addContact(uid: String, cid: String, contactInformation: ContactInformation): Result<Unit>
    suspend fun getUserById(uid: String): Result<UserInformation>
    suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>>
}