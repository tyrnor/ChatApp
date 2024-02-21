package com.example.chatapp.domain.usecase.database

import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserByDisplayNameUseCase @Inject constructor(private val databaseRepository: DatabaseRepository){
    suspend operator fun invoke(query: String) : Flow<Result<List<UserInformation>>> {
        return databaseRepository.searchUsersByDisplayName(query)
    }
}