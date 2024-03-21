package com.example.chatapp.domain.repository

import com.example.chatapp.data.model.ContactInformation
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.UserInformation
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit>
    suspend fun addContact(
        uid: String,
        cid: String,
        contactInformation: ContactInformation,
    ): Result<Unit>

    suspend fun getUserById(uid: String): Result<UserInformation>
    suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>>
    suspend fun findOrCreateChat(otherUserId: String): Result<String>
    suspend fun listenForMessages(chatId: String): Flow<List<Message>>
    suspend fun addMessage(chatId: String, text: String): Result<Unit>
}