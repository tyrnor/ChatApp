package com.example.chatapp.data.repository

import com.example.chatapp.data.model.Chat
import com.example.chatapp.data.model.ContactInformation
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.data.source.remote.FirebaseDatabaseService
import com.example.chatapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val databaseService: FirebaseDatabaseService) :
    DatabaseRepository {

    override suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit> {
        return databaseService.addUser(uid, userInfo)
    }

    override suspend fun addContact(
        uid: String,
        cid: String,
        contactInformation: ContactInformation,
    ): Result<Unit> {
        return databaseService.addContact(uid, cid, contactInformation)
    }

    override suspend fun getUserById(uid: String): Result<UserInformation> {
        return databaseService.getUserById(uid)
    }

    override suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>> {
        return databaseService.searchUsersByDisplayName(query)
    }

    override suspend fun findOrCreateChat(
        currentUserId: String,
        otherUserId: String,
    ): Result<String> {
        return databaseService.findOrCreateChat(currentUserId, otherUserId)
    }

    override suspend fun listenForMessages(chatId: String): Flow<List<Message>> {
        return databaseService.listenForMessages(chatId)
    }

    override suspend fun addMessage(chatId: String, currentUserId: String, otherUserId: String, text: String): Result<Unit> {
        return databaseService.addMessage(chatId, currentUserId, otherUserId, text)
    }

    override suspend fun getUserChats(currentUserId: String): Flow<List<Chat>> {
        return databaseService.getUserChats(currentUserId)
    }

}