package com.example.chatapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.usecase.database.FindOrCreateChatUseCase
import com.example.chatapp.domain.usecase.database.GetUserByIdUseCase
import com.example.chatapp.domain.usecase.database.ListenForMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val findOrCreateChatUseCase: FindOrCreateChatUseCase,
    private val listenForMessagesUseCase: ListenForMessagesUseCase
) : ViewModel() {
    private val _userInformation = MutableStateFlow<Result<UserInformation?>>(Result.success(null))
    val userInformation: StateFlow<Result<UserInformation?>> = _userInformation

    private val _chatId = MutableStateFlow<String?>(null)
    val chatId: StateFlow<String?> = _chatId

    fun fetchUserById(uid: String) {
        viewModelScope.launch {
            val result = getUserByIdUseCase(uid)
            _userInformation.value = result
        }
    }

    fun getChatId(currentUserId: String, otherUserId: String) {
        viewModelScope.launch {
            val result = findOrCreateChatUseCase(currentUserId, otherUserId)
            when {
                result.isSuccess -> {
                    _chatId.value = result.getOrNull()
                }
                result.isFailure -> {
                    _chatId.value = null
                }
            }
        }
    }
}