package com.example.chatapp.di

import com.example.chatapp.data.repository.DatabaseRepositoryImpl
import com.example.chatapp.data.source.remote.FirebaseDatabaseService
import com.example.chatapp.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDataService() : FirebaseDatabaseService = FirebaseDatabaseService()

    @Provides
    @Singleton
    fun provideDatabaseRepository(databaseService: FirebaseDatabaseService) : DatabaseRepository = DatabaseRepositoryImpl(databaseService)
}