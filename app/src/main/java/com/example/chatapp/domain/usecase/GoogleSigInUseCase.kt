package com.example.chatapp.domain.usecase

import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class GoogleSigInUseCase @Inject constructor(private val authRepository: AuthRepository){
    suspend operator fun invoke(token: String) : Result<AuthenticatedUser> {
        return authRepository.googleSignIn(token)
    }
}