package com.example.chatapp.data.repository

import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.data.source.remote.FirebaseDatabaseService
import com.example.chatapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val databaseService: FirebaseDatabaseService) :
    DatabaseRepository {
    override suspend fun addUser(uid: String, userInfo: UserInformation): Result<Unit> {
        return try {
            databaseService.addUser(uid, userInfo).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}