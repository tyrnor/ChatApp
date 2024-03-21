package com.example.chatapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.usecase.database.AddMessageUseCase
import com.example.chatapp.domain.usecase.database.FindOrCreateChatUseCase
import com.example.chatapp.domain.usecase.database.GetUserByIdUseCase
import com.example.chatapp.domain.usecase.database.ListenForMessagesUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val findOrCreateChatUseCase: FindOrCreateChatUseCase,
    private val listenForMessagesUseCase: ListenForMessagesUseCase,
    private val addMessageUseCase: AddMessageUseCase,
) : ViewModel() {
    private val _userInformation = MutableStateFlow<Result<UserInformation?>>(Result.success(null))
    val userInformation: StateFlow<Result<UserInformation?>> = _userInformation

    private val _chatId = MutableStateFlow<String?>(null)
    val chatId: StateFlow<String?> = _chatId

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages


    fun fetchUserById(uid: String) {
        viewModelScope.launch {
            val result = getUserByIdUseCase(uid)
            _userInformation.value = result
        }
    }

    fun getChatId(currentUserId: String, otherUserId: String) {
        val userId = Firebase.auth.currentUser?.uid
        Log.d("TAG", "getChatId: $userId")
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

    fun listenForMessages(chatId: String) {
        viewModelScope.launch {
            listenForMessagesUseCase(chatId).collect { messagesList ->
                _messages.value = messagesList
            }
        }
    }

    fun addMessage(chatId: String, text: String): Result<Unit> {
        return try {
            viewModelScope.launch {

                addMessageUseCase(chatId, text)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}