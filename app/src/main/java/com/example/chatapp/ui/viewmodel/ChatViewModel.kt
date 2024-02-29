package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.usecase.database.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
) : ViewModel() {
    private val _userInformation = MutableStateFlow<Result<UserInformation?>>(Result.success(null))
    val userInformation: StateFlow<Result<UserInformation?>> = _userInformation

    fun fetchUserById(uid: String) {
        viewModelScope.launch {
            val result = getUserByIdUseCase(uid)
            _userInformation.value = result
        }
    }
}