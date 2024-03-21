package com.example.chatapp.domain.usecase.database

import com.example.chatapp.data.model.Chat
import com.example.chatapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserChatsUseCase @Inject constructor(private val databaseRepository: DatabaseRepository){
    suspend operator fun invoke(currentUserId: String) : Flow<List<Chat>> {
        return databaseRepository.getUserChats(currentUserId)
    }
}