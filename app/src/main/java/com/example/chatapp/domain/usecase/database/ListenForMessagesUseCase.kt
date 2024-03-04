package com.example.chatapp.domain.usecase.database

import com.example.chatapp.data.model.Message
import com.example.chatapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenForMessagesUseCase @Inject constructor(private val databaseRepository: DatabaseRepository){
    suspend operator fun invoke(chatId: String) : Flow<List<Message>> {
        return databaseRepository.listenForMessages(chatId)
    }
}