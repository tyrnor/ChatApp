package com.example.chatapp.domain.usecase.auth

import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository){

    suspend operator fun invoke(email: String, password: String) : Result<AuthenticatedUser>{
        return authRepository.login(email, password)
    }
}