package com.example.chatapp.domain.usecase.database

import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val databaseRepository: DatabaseRepository){
    suspend operator fun invoke(uid: String) : Result<UserInformation> {
        return databaseRepository.getUserById(uid)
    }
}