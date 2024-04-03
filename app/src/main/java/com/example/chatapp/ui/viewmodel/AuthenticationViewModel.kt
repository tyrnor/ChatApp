package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.FirebaseAuthErrorCodes
import com.example.chatapp.common.MessageSelector
import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.data.model.AuthenticationException
import com.example.chatapp.data.model.UserInformation
import com.example.chatapp.domain.model.LoginState
import com.example.chatapp.domain.usecase.auth.LoginUseCase
import com.example.chatapp.domain.usecase.auth.RegisterUseCase
import com.example.chatapp.domain.usecase.auth.UpdateProfileUseCase
import com.example.chatapp.domain.usecase.database.AddUserUseCase
import com.google.firebase.FirebaseTooManyRequestsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _user = MutableStateFlow(AuthenticatedUser("", "", ""))
    val user: StateFlow<AuthenticatedUser> = _user

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

    fun register(email: String, password: String, displayName: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val registrationResult = registerUseCase(email, password)
                registrationResult.fold(
                    onSuccess = { authenticatedUser ->
                        val updateProfileResult = updateProfileUseCase(authenticatedUser.userId, displayName)
                        updateProfileResult.fold(
                            onSuccess = {
                                val userInfo = UserInformation(displayName, email)
                                val addUserResult = addUserUseCase(authenticatedUser.userId, userInfo)
                                addUserResult.fold(
                                    onSuccess = {
                                        _user.value = authenticatedUser.copy(displayName = displayName)
                                        login(email, password)
                                    },
                                    onFailure = { exception ->
                                        _loginState.value = LoginState.Error("Failed to add user to database: ${exception.message}")
                                    }
                                )
                            },
                            onFailure = { exception ->
                                _loginState.value = LoginState.Error("Failed to update profile: ${exception.message}")
                            }
                        )
                    },
                    onFailure = { exception ->
                        when (exception) {
                            is AuthenticationException -> {
                                val message = MessageSelector.getMessageFromCode(exception.errorCode)
                                _loginState.value = LoginState.Error(message)
                            }
                            else -> {
                                _loginState.value = LoginState.Error("Registration failed: ${exception.message}")
                            }
                        }
                    }
                )
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An unknown error occurred during registration")
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

