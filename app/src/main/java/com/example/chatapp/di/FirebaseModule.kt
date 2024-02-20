package com.example.chatapp.di

import com.example.chatapp.data.repository.AuthRepositoryImpl
import com.example.chatapp.data.source.remote.FirebaseAuthService
import com.example.chatapp.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthService() : FirebaseAuthService = FirebaseAuthService()


    @Provides
    @Singleton
    fun provideAuthRepository(service: FirebaseAuthService) : AuthRepository = AuthRepositoryImpl(service)


}