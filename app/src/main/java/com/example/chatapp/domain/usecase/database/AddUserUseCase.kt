package com.example.chatapp.domain.usecase.database

import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(uid: String, userInfo: UserInformation): Result<Unit> {
        return databaseRepository.addUser(uid, userInfo)
    }
}