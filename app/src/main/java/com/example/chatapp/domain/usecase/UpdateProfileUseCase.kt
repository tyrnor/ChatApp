package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userId: String, displayName: String) : Result<Unit> {
        return authRepository.updateUserProfile(userId, displayName)
    }
}