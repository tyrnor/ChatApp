package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.model.ContactInformation
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.usecase.database.AddContactUseCase
import com.example.chatapp.domain.usecase.database.SearchUserByDisplayNameUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserByDisplayNameUseCase: SearchUserByDisplayNameUseCase,
    private val addContactUseCase: AddContactUseCase,
) :
    ViewModel() {

    private val _searchResults = MutableStateFlow<Result<List<UserInformation>>>(
        Result.success(
            emptyList()
        )
    )
    val searchResults: StateFlow<Result<List<UserInformation>>> = _searchResults

    fun searchUsers(query: String) {
        if (query.isBlank() || query.isEmpty()) {
            _searchResults.value = Result.success(emptyList())
        } else {
            viewModelScope.launch {
                searchUserByDisplayNameUseCase(query).collect { result ->
                    _searchResults.value = result
                }
            }
        }
    }

    fun addContact(uid: String, userInformation: UserInformation): Result<Unit> {
        return try {
            val cid = userInformation.uid
            val contactInformation = ContactInformation(
                userInformation.displayName,
                userInformation.email,
                Timestamp.now()
            )
            viewModelScope.launch {
                addContactUseCase(uid, cid, contactInformation)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}