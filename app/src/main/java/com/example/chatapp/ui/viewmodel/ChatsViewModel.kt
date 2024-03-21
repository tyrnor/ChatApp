package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.model.Chat
import com.example.chatapp.domain.usecase.database.GetUserChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getUserChatsUseCase: GetUserChatsUseCase,
) : ViewModel() {
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats

    fun getUserChats(currentUserId: String) {
        viewModelScope.launch {
            getUserChatsUseCase(currentUserId).collect { chatsList ->
                _chats.value = chatsList
            }
        }
    }
}