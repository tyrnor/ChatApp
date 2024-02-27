package com.example.chatapp.data.repository

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

    override suspend fun searchUsersByDisplayName(query: String): Flow<Result<List<UserInformation>>> {
        return databaseService.searchUsersByDisplayName(query)
    }

}