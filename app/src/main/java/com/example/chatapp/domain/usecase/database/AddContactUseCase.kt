package com.example.chatapp.domain.usecase.database

import com.example.chatapp.data.model.ContactInformation
import com.example.chatapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddContactUseCase @Inject constructor(private val databaseRepository: DatabaseRepository){
    suspend operator fun invoke(uid: String, cid: String, contactInformation: ContactInformation) : Result<Unit> {
        return databaseRepository.addContact(uid, cid, contactInformation)
    }
}