package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.FirebaseAuthErrorCodes
import com.example.chatapp.common.MessageSelector
import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.data.model.AuthenticationException
import com.example.chatapp.domain.model.LoginState
import com.example.chatapp.domain.usecase.LoginUseCase
import com.google.firebase.FirebaseTooManyRequestsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _user = MutableStateFlow<AuthenticatedUser?>(null)
    val user: StateFlow<AuthenticatedUser?> = _user

    private val _loginFailed = MutableStateFlow(false)
    val loginFailed : StateFlow<Boolean> = _loginFailed

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val result = loginUseCase(email, password)
                result.fold(
                    onSuccess = { user ->
                        _loginState.value = LoginState.Success
                        _user.value = user
                    },
                    onFailure = { exception ->
                        when (exception) {
                            is AuthenticationException -> {
                                val message = MessageSelector.getMessageFromCode(exception.errorCode)
                                _loginState.value = LoginState.Error(message)
                            }
                            is FirebaseTooManyRequestsException -> {
                                _loginState.value = LoginState.Error(MessageSelector.getMessageFromCode(FirebaseAuthErrorCodes.ERROR_TOO_MANY_REQUESTS))
                            }
                            else -> {
                                _loginState.value = LoginState.Error(exception.message ?: "An unknown error occurred")
                            }
                        }
                    }
                )
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun loginFailed(){
        _loginFailed.value = true
    }
    fun resetLoginFailed() {
        _loginFailed.value = false
    }
}

