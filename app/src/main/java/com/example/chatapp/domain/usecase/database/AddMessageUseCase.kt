package com.example.chatapp.domain.usecase.database

import com.example.chatapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddMessageUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(chatId: String, text: String): Result<Unit> {
        return databaseRepository.addMessage(chatId, text)
    }
}