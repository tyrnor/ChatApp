package com.example.chatapp.domain.usecase.database

import com.example.chatapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class FindOrCreateChatUseCase @Inject constructor(private val databaseRepository: DatabaseRepository){
    suspend operator fun invoke(otherUserId: String): Result<String> {
        return databaseRepository.findOrCreateChat(otherUserId)
    }
}